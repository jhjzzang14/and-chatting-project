var express = require('express');

var app = express();

var Login = require('./api/Login')(app);

var server = app.listen(3000,function(){
    console.log('server start');
});
