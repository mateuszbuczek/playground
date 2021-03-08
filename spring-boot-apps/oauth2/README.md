#### Resource Owner
- entity that grants access to protected resource. Usually it is the end user
#### Resource Server
- server that is hosting protected resources
- accepts and respond to protected resource requests using access tokens
#### Client
- application making calls on behalf of resource owner and with its authorization
#### Authorization Server
- server issuing access tokens to the client after successful authenticating the resource owner

#### Grant types
- proof key for code exchange (PKCE) flow
- authorization code grant flow
- client credentials grant flow
- implicit grant flow
- resource owner credentials grant flow

#### Endpoints
- oauth/authorize?response_type=code&client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI
- oauth/token
- oauth/token_info
- oauth/logout
