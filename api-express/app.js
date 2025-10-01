const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');
const mongoose = require("mongoose");

require("dotenv").config(); // Ler as variáveis do arquivo .env

const { MONGO_URL } = process.env;

// Configurando conexão com MongoDB
mongoose.connect(MONGO_URL, {
    minPoolSize: 10,
    socketTimeoutMS: 60000
})
    .then(() => {
        console.log("Conectado ao MongoDB! Aeeee!!");
    })
    .catch(error => {
        console.error("Deu zica na conexão!");
        console.error(error);
    });
    
const indexRouter = require('./routes/index');
const usersRouter = require('./routes/users');
const { error } = require('console');

var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);

module.exports = app;
