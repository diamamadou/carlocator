const express = require('express');
const app = express();
const bodyParser = require('body-parser');

app.use(bodyParser.json({limit: '50mb', extended: true}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true }));

let locators = [];
app.post('/add', (req, res) => {
    const {carName, date, lng, lat} = req.body;

    const locator = {carName, date, lng, lat, id: locators.length};
    locators.push(locator);
    res.status(200).json({error: false, message: 'success'});
});

app.get('/getAll', (req, res) => {
    res.status(200).json(locators);
});

app.listen(3000, () => {
    console.log('Listenning on server 3000');
});