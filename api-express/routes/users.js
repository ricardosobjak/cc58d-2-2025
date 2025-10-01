//----- Importação das bibliotecas
var express = require('express');
var router = express.Router();


//----- Definições variáveis e constantes
const users = []; // Array de usuarios (Banco de dados);


//----- Definição dos Endpoints

// Obter todos os usuarios
router.get('/', function(req, res, next) {
  res.json(users);
});

//Obter um usuario por ID
router.get('/:id', (req, res) => {
  res.json({ message: "Uma pessoa" });
});


//Criar um usuário
router.post('/', (req, res) => {
  // Obter o JSON vindo pelo Body da requisição HTTP
  const user = req.body;

  console.log("Usuário: ", user);

  // Adicionar o banco de dados
  users.push(user);

  res.json(user);
});

//Atualizar um usuário
router.put('/:id', (req, res) => {
  res.json({ message: "atualizar pessoa" });
});

//Deletar um usuário
router.delete('/:id', (req, res) => {
  res.json({ message: "deletar pessoa" });
});






module.exports = router;
