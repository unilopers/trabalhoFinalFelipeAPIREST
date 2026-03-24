from locust import HttpUser, task, between
import random

marcas = ["Honda", "Yamaha", "Suzuki", "Kawasaki", "BMW"]
modelos = ["Sport", "Cruiser", "Trail", "Scooter", "Naked"]

class MotoUser(HttpUser):
    wait_time = between(1, 3)
    moto_id = None

    headers = {
        "Content-Type": "application/json",
        "x-api-key": "QP8nd0ri3zZ2OV39rxSnXSzqNfI9Fx3CZBWYGuI24KY"
    }

    @task(1)
    def criar_moto(self):
        payload = {
            "marca": random.choice(marcas),
            "modelo": random.choice(modelos),
            "ano": random.randint(2000, 2024),
            "preco": round(random.uniform(5000.0, 80000.0), 2),
            "cilindrada": random.choice([125, 150, 250, 300, 400, 600, 750, 1000])
        }

        response = self.client.post(
            "/api/moto",
            json=payload,
            headers=self.headers
        )

        if response.status_code in (200, 201):
            data = response.json()
            self.moto_id = data.get("id")

    @task(2)
    def buscar_moto(self):
        if self.moto_id:
            self.client.get(
                f"/api/moto/{self.moto_id}",
                headers=self.headers
            )