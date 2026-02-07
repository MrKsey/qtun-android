# qtun-android

Android plugin for [Shadowsocks](https://github.com/shadowsocks/shadowsocks-android) based on [qtun](https://github.com/nickolastone/qtun), an IETF-QUIC SIP003 plugin.

## Features

- IETF-QUIC transport for Shadowsocks
- ACME certificate compatibility
- BBR congestion control
- Low resource usage

## Building

### Prerequisites

- Android SDK (API 35) with NDK 27.2.12479018
- JDK 17
- Rust toolchain (via [rustup](https://rustup.rs/)) with Android targets:

```bash
rustup target add armv7-linux-androideabi aarch64-linux-android i686-linux-android x86_64-linux-android
```

### Build

```bash
./gradlew assembleDebug
```

### Run tests

```bash
# Unit tests
./gradlew test

# Instrumented tests (requires a connected device or emulator)
./gradlew connectedAndroidTest
```

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.
