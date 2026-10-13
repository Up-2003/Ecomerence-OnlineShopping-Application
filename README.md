# 🛒 ShopHub — Production-Ready E-Commerce Platform

> Full-stack e-commerce web application built with **Spring Boot 3.2**, **Thymeleaf**, **MySQL 8**, and **Spring Security**. Designed with real-world production concerns: security hardening, observability, database migrations, containerization, and CI/CD readiness.

[![CI](https://github.com/your-org/shophub/actions/workflows/ci.yml/badge.svg)](https://github.com/your-org/shophub/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/your-org/shophub/branch/main/graph/badge.svg)](https://codecov.io/gh/your-org/shophub)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-ready-blue.svg)](https://www.docker.com/)

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Screenshots](#-screenshots)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Quick Start](#-quick-start)
- [Environment Configuration](#-environment-configuration)
- [Database & Migrations](#-database--migrations)
- [Security](#-security)
- [Production Deployment](#-production-deployment)
- [Observability](#-observability)
- [Testing](#-testing)
- [Project Structure](#-project-structure)
- [Troubleshooting](#-troubleshooting)
- [Roadmap](#-roadmap)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Overview

**ShopHub** is a beginner-friendly yet production-ready e-commerce monolith. It demonstrates how to build a real-world web application with:

- 🖥️ **Server-side rendering** — No SPA complexity, just Thymeleaf + Bootstrap
- 🔐 **Proper authentication & authorization** — Session-based with BCrypt
- 🗄️ **Versioned database schema** — Flyway migrations
- 🐳 **Containerized** — Docker & Docker Compose for local dev
- ⚙️ **12-Factor App** — All config via environment variables
- 📊 **Observable** — Health checks, metrics, structured logging

**Why Monolith?** Start simple. Ship fast. Split into microservices only when you have real scaling pressure. This project is architected so that future extraction into microservices is straightforward.

---

## ✨ Features

### 👤 Customer

| Feature | Status |
|---|---|
| Register / Login (BCrypt + Session) | ✅ |
| Browse products (paginated) | ✅ |
| Search (name + description) | ✅ |
| Filter (category, price range) | ✅ |
| Sort (newest, price, name) | ✅ |
| Shopping cart (DB-persisted) | ✅ |
| Checkout with stock validation | ✅ |
| Order history & detail | ✅ |
| Remember-me (7 days) | ✅ |

### 🛠️ Admin

| Feature | Status |
|---|---|
| Dashboard with KPIs | ✅ |
| Product CRUD (soft delete) | ✅ |
| Category CRUD | ✅ |
| Order management (status workflow) | ✅ |
| User management | ✅ |
| Role-based access control | ✅ |

### 🚀 Production Concerns

| Feature | Status |
|---|---|
| Flyway migrations | ✅ |
| Health checks (`/actuator/health`) | ✅ |
| Metrics (Prometheus-ready) | ✅ |
| Structured logging (JSON) | ✅ |
| Rate limiting (Bucket4j) | ✅ |
| CSRF protection | ✅ |
| Security headers (HSTS, CSP) | ✅ |
| Graceful shutdown | ✅ |
| Docker & Compose | ✅ |
| GitHub Actions CI | ✅ |
| Multi-stage Dockerfile | ✅ |

---

## 📸 Screenshots

| Home | Product List | Cart |
|---|---|---|
| ![Home](docs/screenshots/home.png) | ![Products](docs/screenshots/products.png) | ![Cart](docs/screenshots/cart.png) |

| Checkout | Admin Dashboard | Order Detail |
|---|---|---|
| ![Checkout](docs/screenshots/checkout.png) | ![Admin](docs/screenshots/admin.png) | ![Order](docs/screenshots/order.png) |

---

## 🏗 Architecture

- **Controllers** — HTTP concerns only, use DTOs
- **Services** — Business logic, transactions
- **Repositories** — Data access via Spring Data JPA
- **Entities** — Persistent domain model

---

## 🛠 Tech Stack

| Layer | Technology | Version |
|---|---|---|
| Language | Java | 21 (LTS) |
| Framework | Spring Boot | 3.2.x |
| Web | Spring MVC + Thymeleaf | 3.1.x |
| Security | Spring Security 6 | 6.2.x |
| Persistence | Spring Data JPA + Hibernate | 6.4.x |
| Database | MySQL | 8.0 |
| Migrations | Flyway | 10.x |
| Cache | Redis | 7.x |
| Validation | Jakarta Bean Validation | 3.0 |
| Build | Maven | 3.9 |
| Container | Docker | 24+ |
| Testing | JUnit 5 + Mockito + Testcontainers | — |
| Observability | Micrometer + Prometheus + Logback JSON | — |
| Rate Limiting | Bucket4j | 8.x |

---

## 🚀 Quick Start

### Prerequisites

- ☕ **Java 17+**
- 📦 **Maven 3.9+** (or use included `./mvnw`)
- 🐳 **Docker 24+** & **Docker Compose v2**

### 1. Clone

```bash
git clone https://github.com/your-org/shophub.git
cd shophub
