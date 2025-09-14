# 🛒 ShopMate – E-Commerce Shopping Cart App

A **Java Swing desktop application** that simulates a modern e-commerce shopping cart.  
Built to demonstrate full-stack concepts such as **UI design, database persistence, and clean architecture**.  

This project is perfect for showcasing my skills in **Java, SQL, Swing UI, and software design**.

---

## ✨ Features

- 📦 **Product catalog** with images, prices, and “Add to cart” buttons  
- 🛍️ **Shopping cart** with quantity management, totals, and tax calculation  
- 💳 **Checkout flow** with customer form and order placement  
- 🗄️ **Database persistence** using **H2 embedded DB** (products, orders, order items)  
- 🎨 **Modern UI design** with **FlatLaf look & feel**  
- 🖼️ **Image handling** via a custom `ImageUtil` (aspect-ratio scaling, placeholders)  
- ⚡ **Clean architecture** with `model`, `service`, `db`, `ui`, and `util` packages  

---

## 🛠️ Tech Stack

- **Language**: Java 17  
- **UI**: Swing + FlatLaf (modern look & feel)  
- **Database**: H2 embedded (file-based, auto-migrated)  
- **Build Tool**: Maven  
- **Libraries**:  
  - [FlatLaf](https://www.formdev.com/flatlaf/) – modern Swing L&F  
  - [Gson](https://github.com/google/gson) – JSON handling (optional persistence)  
  - [H2](https://www.h2database.com/) – embedded database  

## 🚀 How to Run

### 1. Clone the repo
```bash
git clone https://github.com/YOUR_USERNAME/shoppingcart.git
cd shoppingcart
```

### 2. Build with Maven
```bash
mvn clean package
```

### 3. Run the app

Option 1 – Run directly from Maven
```bash
mvn exec:java -Dexec.mainClass="app.Main"
```

Option 2 – Run the shaded JAR:

```bash
java -jar target/shoppingcart-1.0-SNAPSHOT-shaded.jar
```

### 📂 Project Structure
```bash
src/main/java
 ├─ app/              # Main frame + entry point
 ├─ db/               # Database connection & migrations
 ├─ model/            # Core domain objects (Product, CartItem, etc.)
 ├─ service/          # Business logic (CartService, OrderService, ProductService)
 ├─ ui/               # Swing panels (Catalog, Cart, Checkout, etc.)
 └─ util/             # Helpers (Pricing, ImageUtil)

src/main/resources
 └─ images/           # Product images (jpg/png)
```

### 🔍 Why This Project Matters

This project demonstrates:

My ability to build desktop applications in Java with a polished UI.

Knowledge of database schema design and SQL queries.

Clean code organization and separation of concerns.

Use of modern tools (Maven, FlatLaf, H2, Gson).

It’s a mini real-world e-commerce app that shows both front-end (UI) and back-end (data, services) skills in one project.

### 📸 Screenshots

<img width="1037" height="726" alt="image" src="https://github.com/user-attachments/assets/2ece6935-8691-4db7-8ef8-2eddd3ddefff" />
<img width="1032" height="725" alt="image" src="https://github.com/user-attachments/assets/4806603b-676b-482c-9fa7-6be65016442d" />
<img width="1009" height="707" alt="image" src="https://github.com/user-attachments/assets/545c9640-063f-4dc1-b22a-1435ad7440ee" />
<img width="1009" height="704" alt="image" src="https://github.com/user-attachments/assets/b0751607-3274-45f8-97c1-d5c81faa5aae" />


