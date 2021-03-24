FROM node:10-alpine

# create folder inside container (ideally src/app because user has read/write access to this folder)
RUN mkdir -p /src/app

# set src/app folder as working dir - relative path can be used inside next run commands
WORKDIR /src/app

COPY package.json /src/app/package.json

RUN npm install

COPY . /src/app

EXPOSE 3000

# run inside container 
CMD [ "npm", "start"]