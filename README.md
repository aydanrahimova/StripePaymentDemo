# Stripe Payment Demo (Backend-Only)

This project demonstrates a **Stripe integration** using **Spring Boot** where users can register, save their cards
securely, and make payments either through **Stripe Checkout** or via **saved cards** (off-session payments).

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- Mapstruct
- Swagger
- Stripe Java SDK
- Lombok
- Gradle
- PostgreSQL

---

## What You Can Do

This backend project allows you to:

### 1. Register Users

- Each user is registered in your system and also created in **Stripe** as a `Customer`.
- A `stripeCustomerId` is stored so we can attach payment methods and charge later.

### 2. Save Cards Securely

- Users save cards using `paymentMethodId` (collected via Stripe Elements on frontend).
- The backend attaches the card to the user’s Stripe customer and stores basic metadata like `last4`, `brand`, etc.
- This way, we **never handle raw card info** — we stay PCI-compliant and safe.

### 3. List & Delete Saved Cards

- Users can view all their saved cards.
- Cards can also be deleted (they're detached from the Stripe customer).

---

## Two Ways to Pay

---

### 1. Stripe Checkout – One-Time Purchase (No Card Saving)

**Endpoint:** `POST /api/payment/checkout`  
**Flow:**

- Accepts a product (name, quantity, price).
- Creates a Stripe **Checkout Session**.
- Returns a session URL → redirect user to Stripe’s secure page.
- User completes payment there.
- After success/failure, Stripe redirects to `/success` or `/cancel`.

**Use Case:** When you want **Stripe to handle UI** and **you don’t store cards**.

### 2. Charge with Saved Card – One-Click Backend Payment

**Endpoint:** `POST /api/payment/charge`  
**Flow:**

- User selects a previously saved card.
- You send `userId`, `cardId`, and `amount` to backend.
- Stripe processes the charge immediately — **no user interaction**.

---

## Security First

You should **never send raw card details** (`cardNumber`, `expMonth`, `cvc`, etc.) to your backend.  
Stripe provides frontend libraries (like Stripe.js or Elements) that securely tokenize the card and return a
`paymentMethodId`.

In this project, you’ll see a class called `CardRequest` with raw card fields — but this is only for demo/explanation
purposes. You should not use this in real apps.

---

## 📬 API Endpoints

| Method   | Endpoint                  | Description                                     |
|----------|---------------------------|-------------------------------------------------|
| `POST`   | `/api/users`              | Register user & create Stripe customer          |
| `POST`   | `/api/cards`              | Save a card                                     |
| `GET`    | `/api/cards?userId=`      | Get all saved cards for user                    |
| `DELETE` | `/api/cards/{id}?userId=` | Delete a saved card                             |
| `POST`   | `/api/payment/checkout`   | Start a Stripe Checkout session                 |
| `POST`   | `/api/payment/charge`     | Charge user via saved card                      |
| `GET`    | `/success`                | Stripe redirects here after successful checkout |
| `GET`    | `/cancel`                 | Stripe redirects here if checkout is canceled   |

---

## Stripe Test Cards

To simulate payments:

| Card Number           | Result     | Notes                   |
|-----------------------|------------|-------------------------|
| `4242 4242 4242 4242` | ✅ Success  | Most common test card   |
| `4000 0000 0000 9995` | ❌ Declined | Simulate failed payment |

Use any 3-digit CVC and any future expiration date.


