const express = require('express')
const mongoClient = require('mongodb').MongoClient

const app = express()
const port = 3001
const url = 'mongodb://localhost:27017'
const databaseName = 'myFirstDatabase'

// Middle ware
app.use(express.json())

// Mongo setup
mongoClient.connect(url, (err, db) => {

  if (err) {
    console.log(`Error while connecting mongo client`)
  } else {
    const myDB = db.db(databaseName)
    const collection = myDB.collection('myTable')

    app.post('/signup', (req, res) => {
      console.log('signup router')
      const newUser = {
        name: req.body.name,
        email: req.body.email,
        password: req.body.password,
      }
      console.log(newUser)

      const query = { email: newUser.email }
      collection.findOne(query, (err, result) => {
        console.log('Result')
        console.log(result)

        if (result == null) {
          console.log('You can insert this record')
          // collection.insertOne()
          collection.insertOne(newUser, (err, result) => {
            res.status(200).send()
          })

          res.send(200).send(JSON.stringify())
        }
        else {
          console.log('We have result')
          res.status(400).send()
        }

      })
    })

    app.post('/login', (req, res) => {

      const query = {
        email: req.body.email,
        password: req.body.password
      }

      collection.findOne(query, (err, result) => {
        if (result != null) {
          console.log('Can send the object for the app')
          const objToSend = {
            name: result.name,
            email: result.email,
          }

          console.log(`objectToSend`)
          console.log(objToSend)

          res.status(200).send(objToSend)
        } else {
          console.log(`Result: ${result}`)
          res.status(400).send()
        }

      })

    })

  }
})

app.get('/', (req, res) => {
  res.send('<h1>Welcome to the homepage</h1>')
})

app.listen(port, () => {
  console.log(`Listening on the port: ${port}`)
})
