```sh
npx shadow-cljs node-repl
npx shadow-cljs browser-repl

npx shadow-cljs watch frontend
```

Install the DB:

See https://www.npmjs.com/package/json-server/v/0.17.0

```sh
npm install -g json-server@0.17.4
```

Start the DB:

```sh
json-server --watch db.json --port 3001 --delay 500
```
