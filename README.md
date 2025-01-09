# Clínica Psicológica Vida Estável

![Logo da Clínica]([link-para-a-imagem-do-logo](https://www.google.com/imgres?q=img%20logo%20clinica%20psicologia%20vida%20estavel&imgurl=https%3A%2F%2Fstatic.vecteezy.com%2Fti%2Fvetor-gratis%2Fp1%2F49184304-arvore-do-vida-logotipo-simbolo-do-beleza-crescimento-forca-saude-icone-silhueta-plantar-natureza-folha-preto-verde-organico-meio-ambiente-vetor.jpg&imgrefurl=https%3A%2F%2Fpt.vecteezy.com%2Farte-vetorial%2F49184304-arvore-do-vida-logotipo-simbolo-do-beleza-crescimento-forca-saude-icone-silhueta-plantar-natureza-folha-preto-verde-organico-meio-ambiente&docid=4bxf6ezw_cauYM&tbnid=KyKosq0F6HjH7M&vet=12ahUKEwi2t4Kh0emKAxXLrJUCHRhBLS4QM3oECD4QAA..i&w=1723&h=980&hcb=2&ved=2ahUKEwi2t4Kh0emKAxXLrJUCHRhBLS4QM3oECD4QAA)) <!-- Adicione um link para a imagem do logo da clínica, se houver -->

## Descrição

O projeto **Clínica Psicológica - Vida Estável** é uma aplicação desenvolvida para gerenciar as atividades de uma clínica de psicologia. O sistema permite o agendamento de consultas e o gerenciamento de pacientes e profissionais.

## Funcionalidades

- **Cadastro de Pacientes**: Permite o registro de informações dos pacientes, incluindo dados pessoais.
- **Agendamento de Consultas**: Facilita o agendamento de consultas entre pacientes e psicólogos, com opções de visualização de horários disponíveis, busca por especialidade médica e etc.
- **Gerenciamento de Profissionais**: Cadastro e gerenciamento de psicólogos, incluindo especializações, horários de atendimento.
- **Relatórios**: Geração de relatórios sobre atendimentos realizados, pacientes ativos e estatísticas de agendamentos.

## Tecnologias Utilizadas

- **Backend**: Java, Spring Boot, Spring Security, Flyway, Swagger, Docker.
- **Banco de Dados**: PostgreSQL.
- **Outras Tecnologias**: JacksonDataFormat, Hateoas, Auth0.

## Instalação

Para rodar o código, certifique-se que o seu Docker está corretamente instalado na máquina.
1.  Clone o repositório: 
git clone https://github.com/Iago-on-github/Clinica_Psicologa_Vida_Est-vel.git

2. Navegue até o diretório:
cd Clinica_Psicologa_Vida_Est-vel

3. Construa a imagem Docker:
docker build -t <nome-da-imagem> .

4. Inicie o contâiner:
docker run -d -p 8080:8080 <nome-da-imagem>

5. Acesse a aplicação:
Abra seu navegador e vá para http://localhost:8080 para acessar a aplicação.
