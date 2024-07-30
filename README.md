## Sockkeeper 

A distributed client connection manager.

- Keeps track of client socket connections.
- Forwards messages from backend services to connected clients.

## APIs
### Accept client connections

`ws://localhost:8888/connect/santanukar`

### Send message to client 
    
`POST http://localhost:8888/send`


Body:

```
    {
        "principal": {
            "id": "santanukar"
        },
        "message": "{\"text\":\"Hello, world!\"}"
    }
```

