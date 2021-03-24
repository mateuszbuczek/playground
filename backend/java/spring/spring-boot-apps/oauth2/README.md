Delegated authorization

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
  - responseType=code
  - exchange authorization code for access token
  - front-channel & back-channel
- implicit grant flow
  - responseType=token
  - get token with skipping authorization code
  - front channel
- client credentials grant flow
  - back channel
- resource owner credentials grant flow
  - back channel

#### Front channel
- semi-secure channel (made from browser)
- get authorization code
#### Back channel
- secure channel (made from backend servers)
- exchange authorization code (and client secret) for token
- access token can be managed on frontend (less secure) or backend with connected client cookie

#### OpenId Connect
- oauth2 - authorization
- openId connect - authentication - integrates with oauth2
- standard set of scopes
- standardized implementation
- 'openid' parameter in scope

- access token and id token
    - responseType=token or code
    - during exchange for access token, ID token is also provided with user info in payload
    - access token can be used to request authorization server for more user info (should be passed if openid scope present in access token payload)

- id token
    - responseType=id_token
    - contains user info in payload

#### single sign-on across sites
- OAUTH2
- SAML

#### Endpoints
- oauth/authorize?response_type=RESPONSE_TYPE&client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI
- oauth/token
- oauth/token_info
- oauth/revoke
