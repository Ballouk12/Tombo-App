FROM node:18-alpine

WORKDIR /app

# Copier les dépendances
COPY package*.json ./
RUN npm install --omit=dev

# Copier le reste du code
COPY . .

EXPOSE 3001
CMD ["npm", "start"]