import express from "express";
import http from "http";
import cors from "cors";
import mysql from "mysql2/promise";
import { Server as IOServer } from "socket.io";
import dotenv from "dotenv";

dotenv.config();

const app = express();
app.use(cors());
app.use(express.json());

const httpServer = http.createServer(app);
const io = new IOServer(httpServer, {
  cors: { origin: "*" }
});

const db = await mysql.createPool({
  host: process.env.MYSQL_HOST,
  user: process.env.MYSQL_USER,
  password: process.env.MYSQL_PASSWORD,
  database: process.env.MYSQL_DB
});

// REST endpoint example
app.get("/inventory", async (req, res) => {
  const [rows] = await db.query("SELECT * FROM inventory");
  res.json(rows);
});

// Poll the notifications table
setInterval(async () => {
  const [rows] = await db.query(
    "SELECT * FROM notifications WHERE processed = 0 ORDER BY id LIMIT 20"
  );

  for (const msg of rows) {
    io.emit("db-update", msg);

    await db.query(
      "UPDATE notifications SET processed = 1 WHERE id = ?",
      [msg.id]
    );
  }
}, 1000);

httpServer.listen(4000, () => console.log("Realtime server running on port 4000"));
