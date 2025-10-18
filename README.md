💻 `Tech Stack & Arquitetura`
Este projeto foi construído utilizando um stack moderno e robusto, focado em escalabilidade, manutenibilidade e boas práticas de desenvolvimento Android.

Linguagem: 100% [Kotlin](https://kotlinlang.org/)

`Arquitetura & Padrões:`

Clean Architecture: Separação clara de responsabilidades entre as camadas de Data, Domain e UI.

MVVM (Model-View-ViewModel): Padrão de design na camada de apresentação.

Padrão de Repositório: Abstrai as fontes de dados (local e remota).

UseCases (Interactors): Encapsulam a lógica de negócio de forma isolada e reutilizável.

Injeção de Dependência: Gerenciada com [Koin](https://insert-koin.io/).

`UI (Apresentação):`

Jetpack Compose: UI declarativa e moderna.

Material 3: Componentes visuais e temas.

Compose Navigation: Navegação entre as telas.

`Programação Assíncrona:`

Kotlin Coroutines & Flow: Para gerenciar operações em background e fluxos de dados reativos.

`Networking:`

Retrofit & OkHttp: Para comunicação com a API REST do Clash of Clans.

`Persistência de Dados:`

Room: Para persistência local de dados (jogadores favoritos).

SQLCipher: Banco de dados Room totalmente criptografado.

`Testes:
`
JUnit & MockK: Para testes unitários da lógica de negócio e ViewModels.

🛡️ `Destaques de Segurança (Security-First Approach)`
A segurança foi um pilar fundamental na construção deste aplicativo, com a implementação de múltiplas camadas de proteção e automação.

### ✅ `Armazenamento Seguro no Dispositivo`
Tokens de API e a chave de criptografia do banco de dados são armazenados de forma segura utilizando EncryptedSharedPreferences, que se integra diretamente com o Android Keystore System para proteção baseada em hardware (quando disponível).

O banco de dados local (Room) é criptografado em disco usando SQLCipher, garantindo que os dados dos jogadores favoritos estejam protegidos mesmo em um dispositivo comprometido.

### ✅ `Comunicação Segura`
A comunicação com a API do Clash of Clans é protegida contra ataques Man-in-the-Middle (MITM) através da implementação de Certificate Pinning via network_security_config.xml. Isso garante que o app se comunique apenas com o servidor autêntico.

### ✅ `Pipeline de DevSecOps Automatizada`
A pipeline de CI/CD no GitHub Actions foi configurada para agir como um "portão de segurança", executando verificações automáticas a cada commit:

Gitleaks: Varredura de segredos (secrets scanning) para prevenir o commit acidental de credenciais e chaves de API.

OWASP Dependency-Check: Análise de vulnerabilidades em todas as bibliotecas de terceiros para mitigar riscos da cadeia de suprimentos de software.

MobSF Scan: Análise estática de segurança (SAST) do código-fonte em busca de padrões de codificação inseguros comuns em aplicativos mobile.




🚀 `Como Executar o Projeto`
Siga os passos abaixo para clonar e executar o projeto localmente.

`Clone o repositório:`

git clone (https://github.com/danilodox/Coc-Tracker.git)

`Obtenha uma Chave de API:`

Você precisará de uma chave de API do Clash of Clans. Crie uma no [Portal de Desenvolvedores da Supercell](https://developer.clashofclans.com/).

`Configure a Chave de API:`

Na pasta raiz do projeto, crie um arquivo chamado local.properties.

Dentro deste arquivo, adicione sua chave no seguinte formato:

API_KEY="SUA_CHAVE_DE_API_AQUI"

`Execute o Aplicativo:`

Abra o projeto no Android Studio e execute-o em um emulador ou dispositivo físico.
