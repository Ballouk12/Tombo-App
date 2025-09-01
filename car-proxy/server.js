// server.js
import express from "express";
import fetch from "node-fetch";
import cors from "cors";

const app = express();
app.use(cors());

// Proxy vers CarQuery API
app.get("/api/makes", async (req, res) => {
  try {
    const response = await fetch(
      "https://www.carqueryapi.com/api/0.3/?cmd=getMakes&sold_in=MA"
    );
    const text = await response.text(); // CarQuery renvoie du JSONP parfois
    res.send(text);
  } catch (error) {
    res.status(500).json({ error: "Erreur lors de la récupération des marques" });
  }
});

app.get("/api/models/:brand", async (req, res) => {
  const brand = req.params.brand;
  if (!brand) {
    return res.status(400).json({ error: "Marque non spécifiée" });
  }
  try {
    const url = `https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/${brand}?format=json`;
    const response = await fetch(url);
    const data = await response.json();
    res.json(data);
  } catch (error) {
    res.status(500).json({ error: "Erreur lors de la récupération des modèles" });
  }
});

app.listen(3001, () => {
  console.log(" Proxy API lancé sur http://localhost:3001");
});
