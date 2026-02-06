#!/bin/bash

set -e

[[ -z "${ANDROID_NDK_HOME}" ]] && ANDROID_NDK_HOME="${ANDROID_HOME}/ndk-bundle"
TOOLCHAIN="$(find ${ANDROID_NDK_HOME}/toolchains/llvm/prebuilt/* -maxdepth 1 -type d -print -quit)/bin"

ABIS=(armeabi-v7a arm64-v8a x86 x86_64)
RUST_TARGETS=(armv7-linux-androideabi aarch64-linux-android i686-linux-android x86_64-linux-android)
CLANG_ARCHS=(armv7a-linux-androideabi aarch64-linux-android i686-linux-android x86_64-linux-android)

MIN_API="$1"
ROOT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
OUT_DIR="$ROOT/build/rust"
QTUN_DIR="$ROOT/src/main/rust/qtun"

BIN="libqtun.so"

for i in "${!ABIS[@]}"; do
    ABI="${ABIS[$i]}"
    TARGET="${RUST_TARGETS[$i]}"
    [[ -f "${OUT_DIR}/${ABI}/${BIN}" ]] && continue

    echo "Build ${BIN} ${ABI} (${TARGET})"

    # Set up the linker for cross-compilation
    CC="${TOOLCHAIN}/${CLANG_ARCHS[$i]}${MIN_API}-clang"
    AR="${TOOLCHAIN}/llvm-ar"

    # Convert target to uppercase with underscores for env var name
    TARGET_UPPER=$(echo "$TARGET" | tr '[:lower:]-' '[:upper:]_')

    mkdir -p "${OUT_DIR}/${ABI}"

    env \
        "CC_${TARGET_UPPER}=${CC}" \
        "AR_${TARGET_UPPER}=${AR}" \
        "CARGO_TARGET_${TARGET_UPPER}_LINKER=${CC}" \
        cargo build \
            --manifest-path "${QTUN_DIR}/Cargo.toml" \
            --target "${TARGET}" \
            --release \
            --bin qtun-client \
    && "${TOOLCHAIN}/llvm-strip" \
        "${QTUN_DIR}/target/${TARGET}/release/qtun-client" \
        -o "${OUT_DIR}/${ABI}/${BIN}" \
    || exit 1
done
