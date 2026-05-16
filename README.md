# Fortify 🛡️

A clean, minimal Android app for tracking your digital subscriptions, managing connected accounts, and staying on top of your monthly spending — all in one place.

---

## Features

- 🔐 **Authentication** — Register and log in with email & password, stored securely on-device
- 📊 **Dashboard** — Overview of monthly costs, active subscriptions, and connected accounts
- 💳 **Subscriptions** — Track recurring payments with billing date, monthly cost, and usage status
- 👤 **Accounts** — Manage digital accounts across platforms with tags (duplicate, inactive, at-risk)
- ➕ **Add Account** — Manually add services or pick from a list of popular platforms
- ⚙️ **Settings** — View logged-in email and reset all app data

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation Compose (NavHost) |
| Architecture | MVVM (ViewModel per feature) |
| Storage | SharedPreferences + JSON |
| UI Structure | Scaffold + BottomNavigationBar |

---

## Getting Started

```bash
# 1. Clone the repository
git clone https://github.com/andreitudor23/fortify-budget-tracking.git

# 2. Open in Android Studio
File → Open → select the Fortify/ folder

# 3. Let Gradle sync finish, then run
Run → Run 'app'  (or press Shift+F10)
```

> Minimum SDK: **API 26 (Android 8.0)**
> Tested on: Pixel 6 emulator, API 37

---

## Project Structure

```
Fortify/
├── app/src/main/java/com/echipappa/fortify/
│   ├── ui/
│   │   ├── auth/           # WelcomeScreen, SignInScreen, SignUpScreen, ...
│   │   ├── dashboard/      # DashboardScreen
│   │   ├── subscriptions/  # SubscriptionsScreen
│   │   ├── accounts/       # AccountsScreen, AddAccountScreen
│   │   ├── navigation/     # NavGraph, BottomNavBar
│   │   └── theme/          # Color, Typography, Theme
│   ├── data/
│   │   ├── model/          # AccountEntity, SubscriptionEntity
│   │   ├── database/       # FortifyDatabase (SharedPreferences)
│   │   └── repository/     # AccountRepository, SubscriptionRepository
│   └── viewmodel/          # AccountViewModel, SubscriptionViewModel
```

---

## Roadmap

- [ ] **Cloud sync** — back up data to Firebase or a REST API
- [ ] **Digital Risk Score** — calculate a security score based on account and subscription health
- [ ] **Spending analytics** — pie charts and monthly trends for subscription costs
- [ ] **Push notifications** — reminders before billing dates

---

## License

MIT License — Copyright (c) 2026 Andrei Tudor

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED.
