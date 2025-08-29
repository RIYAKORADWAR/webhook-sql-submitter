# webhook-sql-submitter

Spring Boot app that, **on startup**, generates a webhook, builds the SQL answer based on your `regNo`, and submits it with JWT in the Authorization header.

---

## 🚀 Run

```bash
./mvnw clean package -DskipTests
java -jar target/webhook-sql-submitter-0.0.1-SNAPSHOT.jar \
  --app.name="John Doe" \
  --app.regNo="REG12347" \
  --app.email="john@example.com"

