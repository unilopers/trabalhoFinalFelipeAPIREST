from locust import HttpUser, task, between
import random

class CaminhaoUser(HttpUser):
    wait_time = between(1, 2)

    def on_start(self):
        self.headers = {
            "X-API-KEY": "X83Sn4eL0pDn4-LzUxJ_FauBl0PGH9pvTnWZOtCnmB0"
        }

        payload = {
            "modelo": f"Modelo-{random.randint(1,1000)}",
            "marca": "Volvo",
            "ano": random.randint(2000, 2025),
            "preco": random.uniform(100000, 500000),
            "capacidadeCarga": random.uniform(10000, 30000)
        }

        response = self.client.post(
            "/api/caminhao",
            json=payload,
            headers=self.headers
        )

        if response.status_code in [200, 201]:
            try:
                self.caminhao_id = response.json().get("id")
            except:
                self.caminhao_id = None
        else:
            self.caminhao_id = None

    @task
    def buscar_caminhao(self):
        if hasattr(self, "caminhao_id") and self.caminhao_id:
            self.client.get(
                f"/api/caminhao/{self.caminhao_id}",
                headers=self.headers
            )