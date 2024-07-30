## Sockkeeper

A distributed client connection manager.

- Keeps track of client socket connections.
- Forwards messages from backend services to connected clients.
  
![Sockkeeper design](https://drive.google.com/file/d/1ZGwobQx5D8B5u44l9aBPluVs5cH9QN2v/view?usp=sharing)

## APIs
### Accept client connection

protocol: ws <br> uri: /connect/{principal_id}

`ws://localhost:8888/connect/santanukar`

### Send message to client 
protocol: http <br> uri: /send <br> method: POST

`POST http://localhost:8888/sendMessage`


Body:

```
    {
        "principal": {
            "id": "santanukar"
        },
        "message": "{\"text\":\"Hello, world!\"}"
    }
```

