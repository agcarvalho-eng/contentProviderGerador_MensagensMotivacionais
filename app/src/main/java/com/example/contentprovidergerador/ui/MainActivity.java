package com.example.contentprovidergerador.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.contentprovidergerador.R;
import com.example.contentprovidergerador.databinding.ActivityMainBinding;

/**
 * A Activity principal e única do aplicativo, seguindo o padrão de "Single-Activity Architecture".
 * Sua responsabilidade é hospedar o contêiner de navegação (NavHostFragment) e configurar
 * os componentes de UI globais, como a Toolbar (barra de ferramentas) e a
 * NavigationView (menu de navegação lateral), utilizando o Jetpack Navigation Component.
 */
public class MainActivity extends AppCompatActivity {

    /** Instância do View Binding para acesso seguro e direto às views do layout. */
    private ActivityMainBinding binding;
    /** Configuração que conecta a ActionBar/Toolbar com o NavController, definindo os destinos de nível superior. */
    private AppBarConfiguration appBarConfiguration;
    /** O controlador central que gerencia a navegação entre os fragments dentro do NavHost. */
    private NavController navController;

    /**
     * Chamado quando a activity é criada pela primeira vez.
     * Este método é responsável por inflar o layout e configurar toda a estrutura de navegação.
     *
     * @param savedInstanceState Se a activity estiver sendo recriada, este Bundle contém
     * os dados mais recentes fornecidos em onSaveInstanceState().
     */
    // Configura a UI e a navegação da Activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passo 1: Define a Toolbar customizada como a ActionBar oficial desta Activity.
        setSupportActionBar(binding.toolbar);

        // Passo 2: Encontra o NavController, que é o responsável por gerenciar a troca de fragments.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Passo 3: Cria a configuração da AppBar.
        // Define quais destinos são considerados "de nível superior" (ex: home, perfil).
        // Nesses destinos, a Toolbar exibirá o ícone de menu (hambúrguer) em vez da seta de "voltar".
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_cadastrar)
                .setOpenableLayout(binding.drawerLayout) // Associa a configuração ao nosso DrawerLayout.
                .build();

        // Passo 4: Conecta a ActionBar (Toolbar) com o NavController.
        // Isso faz com que o título da Toolbar e o ícone de navegação (menu/voltar) sejam
        // atualizados automaticamente conforme o usuário navega.
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Passo 5: Conecta a NavigationView (o menu lateral) com o NavController.
        // Isso permite que o clique em um item de menu acione a navegação para o destino
        // correspondente de forma automática.
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    /**
     * Sobrescrito para delegar o evento de clique no botão "Up" (a seta de voltar na Toolbar)
     * para o NavController. Sem isso, a navegação de volta pela Toolbar não funcionaria.
     *
     * @return true se a navegação "Up" foi bem-sucedida, false caso contrário.
     */
    // Delega o evento do botão "Up" (voltar) da Toolbar para o NavController.
    @Override
    public boolean onSupportNavigateUp() {
        // Tenta navegar para cima na pilha de navegação. Se não for possível, recorre ao comportamento padrão.
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}