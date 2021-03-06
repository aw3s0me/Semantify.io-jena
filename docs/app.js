/* Little script to communicate Node.js with an API Rest */
var request = require('request');
var fs = require('fs');
var url = 'http://localhost:8080/rest/ontologies'

req = request.post(
        url,
        function (error, response, body) {

            if (!error && response.statusCode == 200) {
                console.log(response.body);
                console.log('ok')
            } else {
                console.log(response.body);
                console.log(response.statusCode);
                console.log('error')
            }
        }
)

var form = req.form();
form.append('schema', fs.createReadStream('books.xsd'));
form.append('instance', fs.createReadStream('books.xml'));

/*
var url = 'http://localhost:8080/rest/instance/list'
req = request.get(
        url,
        function (error, response, body) {

            if (!error && response.statusCode == 200) {
                console.log(body);
                console.log('ok')
            } else {
                console.log(response);
                console.log(response.statusCode);
                console.log('error')
            }
        }
)

var url = 'http://localhost:8080/rest/instance/load/2'
req = request.get(
        url,
        function (error, response, body) {

            if (!error && response.statusCode == 200) {
                console.log(body);
                console.log('ok')
            } else {
                console.log(response);
                console.log(response.statusCode);
                console.log('error')
            }
        }
)
*/
