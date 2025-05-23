#!/bin/sh
if [ "$(uname)" = "Darwin" ] || [ "$(uname)" = "Linux" ]; then
    export $(grep -v '^#' .env | xargs)
    echo "Token configurado no ambiente Unix/Mac."

elif [ "$OS" = "Windows_NT" ]; then
    set $(findstr /g .env)
    echo "Token configurado no Windows."
else
    echo "Sistema não reconhecido!"
fi
