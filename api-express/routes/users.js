//----- Importação das bibliotecas
var express = require('express');
const User = require('../models/User');
const { default: mongoose } = require('mongoose');
const isAutenticated = require('../middlewares/isAuthenticated');
var router = express.Router();


//----- Definições variáveis e constantes
const users = []; // Array de usuarios (Banco de dados);


//----- Definição dos Endpoints

// Obter todos os usuarios
router.get('/', [isAutenticated], async function(req, res, next) {
  const { name = '' } = req.query;

  return res.json(
    await User.find({name: { $regex: '.*' + name + '.*' } }));
});

//Obter um usuario por ID
router.get('/:id', isAutenticated, async (req, res) => {
  const { id } = req.params;

  let _id = null;

  try {
    _id = new mongoose.Types.ObjectId(id);
  } catch(error) {
    return res.status(400).json({message: "ID inválido"});
  }

  const user = await User.findById(id);
  return user 
    ? res.json(user)
    : res.status(404).json({ message: "User não ecxiste!!" });
});


//Criar um usuário
router.post('/', async (req, res) => {
  // Obter o JSON vindo pelo Body da requisição HTTP
  const json = req.body;

  const user = new User(json);
  console.log("Usuário: ", user);

  const hasErros = user.validateSync(); // Faz validação 

  return hasErros 
    ? res.status(400).json(hasErros) // Envia a resp. com os erros
    : res.json(await user.save()); // Salva o usuário e envia a resposta
});

//Atualizar um usuário
router.put('/:id', isAutenticated, (req, res) => {
  res.json({ message: "atualizar pessoa" });
});

//Deletar um usuário
router.delete('/:id', isAutenticated, (req, res) => {
  res.json({ message: "deletar pessoa" });
});






module.exports = router;
