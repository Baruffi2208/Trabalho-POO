package loja;

import java.util.Scanner;

class ResultadoLogin {
    public boolean vaiProMenu;
    public Usuario usuarioLogado;
}

public class Loja {
		
		private Usuario[] usuarios;
		
		public static void main(String[] args)
		{								
			Boolean vaiProMenu = false;
			Loja loja = new Loja();
			String escolha = "";
			Scanner sc = new Scanner(System.in);
			Usuario usuarioLogado = null;
						
			while(!vaiProMenu)
			{
				ResultadoLogin retorno = loja.menuUsuario(sc, escolha, loja);
				vaiProMenu = retorno.vaiProMenu;
				usuarioLogado = retorno.usuarioLogado;				
			}		
						
			if(vaiProMenu)
			{							
				while(vaiProMenu)
				{					
					System.out.println("=================================");
					System.out.println("BEM VINDO A LOJA");					
					System.out.println("[1] CONSULTAR PRODUTOS");
					System.out.println("[2] CADASTRAR PRODUTOS");	
					System.out.println("[3] CONSULTAR PEDIDOS");
					System.out.println("[4] CADASTRAR PEDIDOS");
					System.out.println("[5] CONSULTAR FORNECEDOR");
					System.out.println("[6] CADASTRAR FORNECEDOR");
					if(usuarioLogado.getNivel() == usuarioLogado.getAdministrador())
					{
						System.out.println("[7] EDITAR USUARIO");
						System.out.println("[8] DELETAR USUARIO");	
						System.out.println("[9] ADICIONAR USUARIOS");
						System.out.println("[10] CONSULTAR USUARIOS");
						System.out.println("[11] EDITAR PRODUTOS");
						System.out.println("[12] DELETAR PRODUTOS");
					}			
					System.out.println("[13] FAZER LOGOUT");
					System.out.println("[0] SAIR DO PROGRAMA");
					System.out.println("=================================");	
					escolha = sc.nextLine();
					switch(escolha)
					{
					    case("7"):
					    {
					    	loja.atualizarUsuario(sc, usuarioLogado);
					    	break;
					    }
					    case("8"):
					    {
					    	loja.deletarUsuario(sc, usuarioLogado);
					    	break;
					    }
					    case("9"):
					    {
					    	loja.criarUsuario(sc);
					    	break;
					    }
					    case("10"):
					    	loja.consultarUsuarios();
					    	break;
					    case "11":
					        loja.editarProduto(sc);
					        break;
					    case "12":
					        loja.deletarProduto(sc);
					        break;
					    case("13"):	   	
						    ResultadoLogin retorno = loja.menuUsuario(sc, escolha, loja);
						    usuarioLogado = retorno.usuarioLogado;
						    					    
					    	break;
						case("0"):
						{
							vaiProMenu = false;
							break;
						}
						default: 
						{
							break;
						}
					}
				}	
			}
			
			System.out.println("OBRIGADO, VOLTE SEMPRE!");
			sc.close();
		}
		
		public ResultadoLogin menuUsuario(Scanner sc, String escolha, Loja loja)
		{
			ResultadoLogin resultado = new ResultadoLogin();
			while(true)
			{				
				System.out.println("=================================");
				System.out.println("BEM VINDO A LOJA");
				System.out.println("[1] REALIZAR LOGIN");
				System.out.println("[2] CRIAR USUARIO");				
				System.out.println("[0] SAIR");
				System.out.println("=================================");	
				escolha = sc.nextLine();
				if("1".equals(escolha))
				{
					
					Usuario userTemp = loja.realizarLogin(sc);
					if(userTemp != null)
					{
						resultado.usuarioLogado = userTemp;
						resultado.vaiProMenu = true;
						break;
					}
					
				}else if("2".equals(escolha))
				{			
					Usuario userTemp = loja.criarUsuario(sc);
					if(userTemp != null)
					{
						System.out.println("Quer logar automaticamente[S/N]");						
						
						if("S".equals(sc.nextLine().toUpperCase()))
						{
							resultado.usuarioLogado = userTemp;
							resultado.vaiProMenu =  true;
							break;
						}else if("N".equals(sc.nextLine().toUpperCase())) {
							resultado.vaiProMenu = false;
						}else {
							System.out.println("Opção invalida");
						}
					}
					
				}
				else if("0".equals(escolha))
				{
					break;
				}
			}
			
			return resultado;
		}
		
		
		public Usuario realizarLogin(Scanner sc)
		{
			System.out.println("Digite o email do usuário");
			String email = sc.nextLine();
			System.out.println("Digite a senha do usuário");
			String senha = sc.nextLine();
				
			Usuario usuario = new Usuario(email, senha);
								
			Usuario tempUsuario = usuario.validaLogin(usuarios);
			if(tempUsuario != null)
			{
				System.out.println("Logado com sucesso");
				
				return tempUsuario;
			}	
			
			return null;
						
		}
		
		public Usuario criarUsuario(Scanner sc)
		{
			System.out.println("Digite o nome do usuario");
			String nome = sc.nextLine();
			System.out.println("Digite o email do usuario");
			String email = sc.nextLine();
			System.out.println("Digite a senha do usuario");
			String senha = sc.nextLine();
			System.out.println("Digite o nível do usuario");
			System.out.println("[1] Administrador");
			System.out.println("[2] Cliente");
			int nivel = sc.nextInt();
			sc.nextLine();
				
			Usuario usuario = new Usuario(nome, email, senha, nivel);

			this.usuarios = usuario.criaUsuario(this.usuarios);
			if(usuarios != null) {
			    System.out.println("Usuario criado com sucesso");
			    System.out.println(usuario);
			    return usuario;
			} else {                
			    System.out.println("Não foi possível cadastrar o usuário");        
			    return null;
			}	
					
		}
		
		public Usuario atualizarUsuario(Scanner sc, Usuario usuarioLogado)
		{
			int ehAdministrador = usuarioLogado.getAdministrador();
			int nivelLogado = usuarioLogado.getNivel();
			
			//Se for administrador poderá editar qualquer usuario
			if(nivelLogado == ehAdministrador && this.usuarios.length > 0)
			{
				for(int i = 0; i < this.usuarios.length; i++)
				{
					if(this.usuarios[i] != null)
					{
						System.out.println("==============================");
						System.out.println(this.usuarios[i]);
						System.out.println("==============================");
					}
					
				}
				
				System.out.println("Qual usuario você deseja editar");
				int escolha = sc.nextInt();
				sc.nextLine();
				
				Usuario usuarioParaAtualizar = null;
				for(int i = 0; i < this.usuarios.length; i++)
				{
					if(usuarios[i] != null)
					{
						if(this.usuarios[i].getUserId() == escolha)
						{
							usuarioParaAtualizar = this.usuarios[i];
							break;
						}
					}					
				}
				
				if(usuarioParaAtualizar != null)
				{
					String nome = usuarioParaAtualizar.getNome();
					System.out.println("Deseja editar o nome?");
					System.out.println("Digite S para editar ou pressione qualquer tecla para continuar");
					
					if("S".equals(sc.nextLine().toUpperCase())){
						System.out.println("Digite o novo nome");
						nome = sc.nextLine();
					}
					
					String email = usuarioParaAtualizar.getEmail();
					System.out.println("Deseja editar o email?");
					System.out.println("Digite S para editar ou pressione qualquer tecla para continuar");
					
					if ("S".equals(sc.nextLine().toUpperCase())) {
					    System.out.println("Digite o novo email:");
					    email = sc.nextLine();
					    System.out.println("Email: " + email);
					}

					String senha = usuarioParaAtualizar.getSenha();
					System.out.println("Deseja editar a senha?");
					System.out.println("Digite S para editar ou pressione qualquer tecla para continuar");
					
					if ("S".equals(sc.nextLine().toUpperCase())) {
					    System.out.println("Digite a nova senha:");
					    senha = sc.nextLine();
					}

					int nivel = usuarioParaAtualizar.getNivel();
					System.out.println("Deseja editar o nível?");
					System.out.println("Digite S para editar ou pressione qualquer tecla para continuar");
					
					if ("S".equals(sc.nextLine().toUpperCase())) {
					    System.out.println("Digite o novo nível:");
					    System.out.println("[1] Administrador");
					    System.out.println("[2] Cliente");
					    nivel = sc.nextInt();
					    sc.nextLine(); // Limpar buffer
					}
					
					usuarioParaAtualizar.atualizaUsuario(nome, email, senha, nivel, this.usuarios);
					
				}else {
					System.out.println("Usuario não encontrado");
					return null;
				}
				
				System.out.println("==============================");
				System.out.println("USUARIO ATUALIZADO COM SUCESSO");
				System.out.println(usuarioParaAtualizar);
				System.out.println("==============================");
					
			}
			
			return null;
			
			
		}
		
		public void deletarUsuario(Scanner sc, Usuario usuarioLogado)
		{
			int ehAdministrador = usuarioLogado.getAdministrador();
			int nivelLogado = usuarioLogado.getNivel();		
			
			//Se for administrador poderá editar qualquer usuario
			if(nivelLogado == ehAdministrador)
			{
				for(int i = 0; i < this.usuarios.length; i++)
				{
					if(usuarios[i] != null)
					{
						System.out.println("==============================");
						System.out.println(this.usuarios[i]);
						System.out.println("==============================");
					}
					
				}
				
				System.out.println("Qual usuario você deseja excluir, Digite o Id");
				int escolha = sc.nextInt();
				sc.nextLine();
				
				Usuario usuarioParaExcluir = null;
				for(int i = 0; i < usuarios.length; i++)
				{
					if(this.usuarios[i] != null)
					{
						if(this.usuarios[i].getUserId() == escolha)
						{
							usuarioParaExcluir = this.usuarios[i];
							break;
						}
					}					
				}
				
				if(usuarioParaExcluir != null)
				{				
					Boolean deleta = usuarioParaExcluir.deletaUsuario(this.usuarios);
					if(deleta)
					{						
						System.out.println("Usuário deletado com sucesso");
					}					
				}else {
					System.out.println("Usuario não encontrado");					
				}		
						
			}
		}
		
		public void consultarUsuarios()
		{
			if(this.usuarios.length > 0)
			{
				for(int i = 0; i < usuarios.length; i++)
				{
					if(usuarios[i] != null)
					{
						System.out.println(usuarios[i] + "\n");					
					}
				}				
			}else {
				System.out.println("Nenhum usuario cadastrado");
			}
		}

		public void cadastrarProduto(Scanner sc) {
		    
		    System.out.println("Digite o nome do produto:");
		    String nome = sc.nextLine();
		    System.out.println("Digite a descrição do produto:");
		    String descricao = sc.nextLine();
		    System.out.println("Digite o preço do produto:");
		    double preco = sc.nextDouble();
		    System.out.println("Digite a quantidade em estoque:");
		    int quantidade = sc.nextInt();
		    sc.nextLine(); // Limpar buffer
		
		    Produto produto = new Produto(nome, descricao, preco, quantidade);
		
		    for (int i = 0; i < estoque.length; i++) {
		        if (estoque[i] == null) {
		            estoque[i] = produto;
		            System.out.println("Produto cadastrado com sucesso!");
		            break;
		        }
		    }
		}

		public void consultarProdutos() {
		    
		    boolean encontrou = false;
		    for (Produto p : estoque) {
		        if (p != null) {
		            System.out.println("=================================");
		            System.out.println(p);
		            encontrou = true;
		        }
		    }
		    if (!encontrou) {
		        System.out.println("Nenhum produto cadastrado.");
		    }
		}

		public void editarProduto(Scanner sc) {
		    
		    consultarProdutos();
		    System.out.println("Digite o ID do produto a ser editado:");
		    int id = sc.nextInt();
		    sc.nextLine();
		
		    for (Produto p : estoque) {
		        if (p != null && p.getId() == id) {
		            System.out.println("Editar nome (atual: " + p.getNome() + ")? S/N");
		            if (sc.nextLine().equalsIgnoreCase("S")) {
		                System.out.println("Novo nome:");
		                p.setNome(sc.nextLine());
		            }
		
		            System.out.println("Editar descrição (atual: " + p.getDescricao() + ")? S/N");
		            if (sc.nextLine().equalsIgnoreCase("S")) {
		                System.out.println("Nova descrição:");
		                p.setDescricao(sc.nextLine());
		            }
		
		            System.out.println("Editar preço (atual: R$" + p.getPreco() + ")? S/N");
		            if (sc.nextLine().equalsIgnoreCase("S")) {
		                System.out.println("Novo preço:");
		                p.setPreco(sc.nextDouble());
		                sc.nextLine();
		            }
		
		            System.out.println("Editar quantidade (atual: " + p.getQuantidade() + ")? S/N");
		            if (sc.nextLine().equalsIgnoreCase("S")) {
		                System.out.println("Nova quantidade:");
		                p.setQuantidade(sc.nextInt());
		                sc.nextLine();
		            }
		
		            System.out.println("Produto atualizado!");
		            return;
		        }
		    }
		
		    System.out.println("Produto não encontrado.");
		}

		public void deletarProduto(Scanner sc) {
		    
		    consultarProdutos();
		    System.out.println("Digite o ID do produto a ser excluído:");
		    int id = sc.nextInt();
		    sc.nextLine();
		
		    for (int i = 0; i < estoque.length; i++) {
		        if (estoque[i] != null && estoque[i].getId() == id) {
		            estoque[i] = null;
		            System.out.println("Produto excluído com sucesso!");
		            return;
		        }
		    }
		
		    System.out.println("Produto não encontrado.");
		}




	
	
}
