// Middleware para interceptar a autenticação do cliente

const isAutenticated = (req, res, next) => {

    const { authorization } = req.headers;

    // Se tiver token
    if(authorization && authorization === "1234") 
        return next();
    
    return res.status(401).json({message: "Token inválido"});
}

module.exports = isAutenticated;