import json
import requests
import hmac
import hashlib
import base64

# Detalhes da sua configuração do Cognito
REGION = "sa-east-1"  # Ex: "us-east-1", "sa-east-1"
USER_POOL_ID = "sa-east-1_bmF2hUkTJ"
CLIENT_ID = "1akie59k5b17a9vtc1q8fted4p"
CLIENT_SECRET = "12r09mb5rbe8prq7rd921fp5o3nmcmhgrtp6jg5uqv2gea9nk7kk"
USERNAME = "ricardosobjak@gmail.com"
PASSWORD = "Melancia123!"

# Calcular o SECRET_HASH
message = bytes(USERNAME + CLIENT_ID, 'utf-8')
key = bytes(CLIENT_SECRET, 'utf-8')
hashed = hmac.new(key, message, hashlib.sha256)
secret_hash = base64.b64encode(hashed.digest()).decode('utf-8')

# Endpoint da API de autenticação do Cognito
url = f"https://cognito-idp.{REGION}.amazonaws.com/"
headers = {
    "Content-Type": "application/x-amz-json-1.1",
    "X-Amz-Target": "AWSCognitoIdentityProviderService.InitiateAuth"
}
payload = {
    "AuthFlow": "USER_PASSWORD_AUTH",
    "ClientId": CLIENT_ID,
    "AuthParameters": {
        "USERNAME": USERNAME,
        "PASSWORD": PASSWORD,
        "SECRET_HASH": secret_hash
    }
}


response = requests.post(url, headers=headers, data=json.dumps(payload))
#response.raise_for_status()  # Levanta uma exceção para códigos de status de erro


auth_result = response.json().get("AuthenticationResult")

print(response.json())

if auth_result:
    id_token = auth_result.get("IdToken")
    access_token = auth_result.get("AccessToken")
    refresh_token = auth_result.get("RefreshToken")

    print("Autenticação bem-sucedida!")
    print(f"ID Token: {id_token}")
    print(f"Access Token: {access_token}")
    print(f"Refresh Token: {refresh_token}")
else:
    print("Erro na autenticação: Resposta inesperada.")
    print(response.json())

