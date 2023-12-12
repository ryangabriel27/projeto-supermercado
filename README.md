Relatório da Situação de Aprendizagem 6 

Introdução:

  No sexto projeto, foi desenvolvido uma aplicação GUI em java que simula um sistema de um mercado. Em suma, a aplicação permite com que seja identificado o tipo de cliente,
podendo ser referido como VIP(Faz parte do clube de descontos) ou não, o programa possui também um sistema de vendas de produtos, que é aplicado através da busca por um código
de barras (serial), a partir desses dados a conclusão da compra e a aplicação de descontos para produtos previamente cadastrados será aplicado caso o cliente seja VIP.
  O programa gerencia o estoque, contabilizando a quantidade de produtos, permitindo a atualização das quantidades e o cadastramento de produtos novos. Contendo todos os dados,
todas as aplicações são conectadas no banco de dados, afim de armazenar e organizar informações de forma estruturada, permitindo acesso, manipulação e recuperação eficientes
dos dados.

Objetivos específicos:

  1.Desenvolver a interface gráfica (GUI) utilizando Swing para proporcionar uma experiência amigável ao usuário.
  2.Implementar a lógica de identificação do cliente como VIP ou não.
  3.Criar funcionalidades para o registro de vendas de produtos através de códigos de barras.
  4.Integrar um banco de dados (Postgresql) para armazenar informações sobre clientes, produtos e vendas.
  5.Desenvolver a lógica de aplicação de descontos para produtos cadastrados, caso o cliente seja VIP.
  6.Implementar o gerenciamento de estoque, permitindo a atualização das quantidades e o cadastramento de novos produtos.
  7.Implementar o gerenciamento de logs do Sistemas.
  
Metodologia:

  No desenvolvimento da aplicação, foi utilizado JDBC, PostgreSQL, VSCode, e predominantemente a lnguagem de programação Java

Resultados/Descobertas:

  A partir das aplicações realizadas no programa, ficou evidente a funcionalidade e aplicabilidade do sistema, no qual foi subdividido em 6 telas principais que ilustram 
a estruturação do código, sendo elas:

  1.Tela de Cadastro de Cliente VIP: Permitir o cadastro de um novo cliente.
  Elementos da Interface - Campos para inserção de dados do cliente (nome, CPF, etc.).
                           Botão para cadastrar o novo cliente. (Todo Cliente Cadastrado é VIP).

  2. Tela de Identificação do Cliente: Permitir que o operador identifique se o cliente é VIP ou não.
  Elementos da Interface - Campo de pesquisa por CPF.
                           Indicação visual do status VIP.
                           Botão para cadastrar novo cliente.

  3. Tela de Registro de Vendas: Registrar as vendas de produtos a partir de um código de barras.
  Elementos da Interface - Campo para inserção do código de barras do produto.
                           Lista dinâmica dos produtos adicionados à venda.
                           Botões para adicionar/remover produtos.
                           Indicação visual de desconto aplicado(Cliente VIP)

  4. Tela de Conclusão da Compra: Finalizar a compra, exibindo o total e permitindo o pagamento.
  Elementos da Interface - Lista final dos produtos, quantidades e preços.
                           Total da compra.
                           Opções de pagamento (dinheiro, cartão, etc.).
                           Botão para finalizar a compra.
                           Imprimir Relatório de Vendas(Cupom Fiscal)

  5. Tela de Gerenciamento de Estoque: Gerenciar o estoque da loja, atualizando quantidades e cadastrando novos produtos.
  Elementos da Interface - Lista de produtos em estoque.
                           Campos para atualização de quantidades.
                           Botões para adicionar novo produto.
                           Indicadores visuais de estoque baixo.

  6. Tela de Cadastro de Novo Produto: Permitir o cadastro de um novo produto no estoque.
  Elementos da Interface - Campos para inserção de dados do novo produto (nome, código de barras, preço, etc.).
                           Botão para cadastrar o novo produto.





Discussão:



Conclusão:



Referências:
