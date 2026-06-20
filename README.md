# qtun-android

[![Build APK](https://github.com/MrKsey/qtun-android/actions/workflows/build.yml/badge.svg)](https://github.com/MrKsey/qtun-android/actions/workflows/build.yml)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Release](https://img.shields.io/github/v/release/MrKsey/qtun-android)](https://github.com/MrKsey/qtun-android/releases)

**English** | [Русский](#русский)

---

## English

### What is qtun?

**qtun** is an IETF-QUIC based SIP003 plugin for Shadowsocks. It provides a modern, efficient transport layer for Shadowsocks connections using the QUIC protocol.

### Features

- 🚀 **IETF-QUIC Transport** - Modern protocol with improved performance over traditional TCP
- 🔒 **TLS 1.3 Support** - Encrypted traffic that looks like regular HTTPS
- 📜 **ACME Certificate Compatibility** - Free SSL/TLS certificates via Let's Encrypt
- 🎛️ **BBR Congestion Control** - Google's congestion control algorithm for better throughput
- ⚡ **Low Resource Usage** - Written in Rust for memory safety and efficiency
- 📱 **Android Support** - Native Android plugin for Shadowsocks clients

### How It Works

qtun acts as a bridge between Shadowsocks and QUIC protocol:

```
┌─────────────┐     ┌──────────┐     ┌─────────┐     ┌──────────┐     ┌─────────┐
│  Shadowsocks│────▶│ qtun-cli │────▶│  QUIC   │────▶│ Internet │────▶│ Server  │
│    Client   │     │ (plugin) │     │ (UDP)   │     │          │     │ (qtun)  │
└─────────────┘     └──────────┘     └─────────┘     └──────────┘     └─────────┘
```

### Parameters

#### Client-side Parameters (for Shadowsocks Android)

| Parameter | Environment Variable | Description | Example |
|-----------|---------------------|-------------|---------|
| `host` | `QTUN_HOST` | Server hostname/IP | `example.com` |
| `port` | `QTUN_PORT` | Server QUIC port | `443` |
| `sni` | `QTUN_SNI` | Server Name Indication (TLS) | `example.com` |
| `alpn` | `QTUN_ALPN` | Application-Layer Protocol Negotiation | `h3` |
| `obfs` | `QTUN_OBFS` | Obfuscation password | `mysecret` |

#### Server-side Parameters (qtun-server)

| Parameter | Description | Default |
|-----------|-------------|---------|
| `--listen` | Listen address | `0.0.0.0:443` |
| `--cert` | TLS certificate path | Required |
| `--key` | TLS private key path | Required |
| `--ss-host` | Shadowsocks server host | `127.0.0.1` |
| `--ss-port` | Shadowsocks server port | `8388` |
| `--alpn` | ALPN protocol | `h3` |

### Example Configuration

#### Shadowsocks Android Plugin Configuration

```json
{
  "server": "example.com",
  "server_port": 8388,
  "password": "your_password",
  "method": "chacha20-ietf-poly1305",
  "plugin": "com.github.shadowsocks.plugin.qtun/.QtunPlugin",
  "plugin_opts": "host=example.com;port=443;sni=example.com;alpn=h3"
}
```

#### Server-side (Linux)

```bash
# Install qtun-server
cargo install qtun

# Run with Let's Encrypt certificate
qtun-server --listen 0.0.0.0:443 \
    --cert /etc/letsencrypt/live/example.com/fullchain.pem \
    --key /etc/letsencrypt/live/example.com/privkey.pem \
    --ss-host 127.0.0.1 \
    --ss-port 8388
```

### Building

#### Prerequisites

- Android SDK (API 35) with NDK 27.2.12479018
- JDK 17
- Rust toolchain with Android targets:
  ```bash
  rustup target add armv7-linux-androideabi aarch64-linux-android
  ```

#### Build from Source

```bash
git clone --recursive https://github.com/MrKsey/qtun-android.git
cd qtun-android
./gradlew assembleRelease
```

#### GitHub Actions

APK files are automatically built and published as releases:
- Go to [Releases](https://github.com/MrKsey/qtun-android/releases)
- Download the appropriate APK for your device:
  - `arm-release.apk` - ARMv7 (32-bit)
  - `arm64-release.apk` - ARM64 (64-bit)

### Installation

1. Download the APK matching your device architecture
2. Enable "Install from Unknown Sources" in Android settings
3. Install the APK
4. Configure Shadowsocks with qtun plugin options

### Usage with Shadowsocks Android

1. Install [Shadowsocks Android](https://github.com/shadowsocks/shadowsocks-android)
2. Install qtun-android plugin
3. Create new profile with your server details
4. In plugin options, add:
   ```
   host=your-server.com;port=443;sni=your-server.com;alpn=h3
   ```
5. Connect and enjoy secure browsing!

### Troubleshooting

| Issue | Solution |
|-------|----------|
| Connection timeout | Check firewall rules, ensure UDP port is open |
| Certificate error | Verify SNI matches certificate domain |
| High battery usage | Enable BBR congestion control on server |
| Plugin not found | Reinstall both Shadowsocks and qtun plugin |

### License

GNU General Public License v3.0 - see [LICENSE](LICENSE) for details.

---

## Русский

### Что такое qtun?

**qtun** — это плагин SIP003 для Shadowsocks, основанный на протоколе IETF-QUIC. Он обеспечивает современный и эффективный транспортный уровень для подключений Shadowsocks с использованием протокола QUIC.

### Возможности

- 🚀 **Транспорт IETF-QUIC** - Современный протокол с улучшенной производительностью по сравнению с TCP
- 🔒 **Поддержка TLS 1.3** - Зашифрованный трафик, выглядящий как обычный HTTPS
- 📜 **Совместимость с ACME** - Бесплатные SSL/TLS сертификаты через Let's Encrypt
- 🎛️ **Контроль перегрузок BBR** - Алгоритм Google для лучшей пропускной способности
- ⚡ **Низкое потребление ресурсов** - Написан на Rust для безопасности памяти и эффективности
- 📱 **Поддержка Android** - Нативный плагин для клиентов Shadowsocks

### Как это работает

qtun выступает в качестве моста между Shadowsocks и протоколом QUIC:

```
┌─────────────┐     ┌──────────┐     ┌─────────┐     ┌──────────┐     ┌─────────┐
│  Shadowsocks│────▶│ qtun-cli │────▶│  QUIC   │────▶│ Internet │────▶│ Server  │
│    Client   │     │ (plugin) │     │ (UDP)   │     │          │     │ (qtun)  │
└─────────────┘     └──────────┘     └─────────┘     └──────────┘     └─────────┘
```

### Параметры

#### Параметры клиента (для Shadowsocks Android)

| Параметр | Переменная окружения | Описание | Пример |
|----------|---------------------|----------|---------|
| `host` | `QTUN_HOST` | Хост/IP сервера | `example.com` |
| `port` | `QTUN_PORT` | Порт QUIC сервера | `443` |
| `sni` | `QTUN_SNI` | Server Name Indication (TLS) | `example.com` |
| `alpn` | `QTUN_ALPN` | Согласование протокола прикладного уровня | `h3` |
| `obfs` | `QTUN_OBFS` | Пароль обфускации | `mysecret` |

#### Параметры сервера (qtun-server)

| Параметр | Описание | По умолчанию |
|----------|----------|--------------|
| `--listen` | Адрес прослушивания | `0.0.0.0:443` |
| `--cert` | Путь к TLS сертификату | Требуется |
| `--key` | Путь к приватному ключу TLS | Требуется |
| `--ss-host` | Хост сервера Shadowsocks | `127.0.0.1` |
| `--ss-port` | Порт сервера Shadowsocks | `8388` |
| `--alpn` | Протокол ALPN | `h3` |

### Пример конфигурации

#### Конфигурация плагина для Shadowsocks Android

```json
{
  "server": "example.com",
  "server_port": 8388,
  "password": "your_password",
  "method": "chacha20-ietf-poly1305",
  "plugin": "com.github.shadowsocks.plugin.qtun/.QtunPlugin",
  "plugin_opts": "host=example.com;port=443;sni=example.com;alpn=h3"
}
```

#### Серверная часть (Linux)

```bash
# Установка qtun-server
cargo install qtun

# Запуск с сертификатом Let's Encrypt
qtun-server --listen 0.0.0.0:443 \
    --cert /etc/letsencrypt/live/example.com/fullchain.pem \
    --key /etc/letsencrypt/live/example.com/privkey.pem \
    --ss-host 127.0.0.1 \
    --ss-port 8388
```

### Сборка

#### Требования

- Android SDK (API 35) с NDK 27.2.12479018
- JDK 17
- Rust toolchain с Android таргетами:
  ```bash
  rustup target add armv7-linux-androideabi aarch64-linux-android
  ```

#### Сборка из исходников

```bash
git clone --recursive https://github.com/MrKsey/qtun-android.git
cd qtun-android
./gradlew assembleRelease
```

#### GitHub Actions

APK файлы автоматически собираются и публикуются в релизах:
- Перейдите в [Releases](https://github.com/MrKsey/qtun-android/releases)
- Скачайте подходящий APK для вашего устройства:
  - `arm-release.apk` - ARMv7 (32-бит)
  - `arm64-release.apk` - ARM64 (64-бит)

### Установка

1. Скачайте APK, соответствующий архитектуре вашего устройства
2. Включите "Установку из неизвестных источников" в настройках Android
3. Установите APK
4. Настройте Shadowsocks с параметрами плагина qtun

### Использование с Shadowsocks Android

1. Установите [Shadowsocks Android](https://github.com/shadowsocks/shadowsocks-android)
2. Установите плагин qtun-android
3. Создайте новый профиль с данными вашего сервера
4. В параметрах плагина добавьте:
   ```
   host=your-server.com;port=443;sni=your-server.com;alpn=h3
   ```
5. Подключайтесь и наслаждайтесь безопасным просмотром!

### Решение проблем

| Проблема | Решение |
|----------|---------|
| Таймаут соединения | Проверьте правила фаервола, убедитесь что UDP порт открыт |
| Ошибка сертификата | Убедитесь что SNI совпадает с доменом сертификата |
| Высокое потребление батареи | Включите контроль перегрузок BBR на сервере |
| Плагин не найден | Переустановите Shadowsocks и плагин qtun |

### Лицензия

GNU General Public License v3.0 — подробности в файле [LICENSE](LICENSE).

---

## Links / Ссылки

- [Shadowsocks Android](https://github.com/shadowsocks/shadowsocks-android)
- [Original qtun](https://github.com/nickolastone/qtun)
- [QUIC Protocol](https://quicwg.org/)
- [Rust Programming Language](https://www.rust-lang.org/)

## Contributing / Вклад

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

Приветствуются pull requests! Для значительных изменений, пожалуйста, сначала откройте issue для обсуждения.
