package com.example.contentprovidergerador.ui;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.contentprovidergerador.R;
import com.example.contentprovidergerador.databinding.ActivityMainBinding;
import com.example.contentprovidergerador.ui.adapter.MensagemAdapter;
import com.example.contentprovidergerador.ui.fragments.FragmentoCadastrar;
import com.example.contentprovidergerador.ui.viewmodel.MensagemViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private MensagemViewModel viewModel;
    private MensagemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configura a Toolbar
        setSupportActionBar(binding.toolbar);

        // Configura o Navigation Drawer (menu lateral)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);

        // Configura o RecyclerView (lógica que estava no FragmentoListar)
        setupRecyclerView();

        // Configura o ViewModel para observar as mensagens
        setupViewModel();
    }

    private void setupRecyclerView() {
        adapter = new MensagemAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MensagemViewModel.class);
        viewModel.getMensagens().observe(this, mensagens -> {
            if (mensagens != null) {
                adapter.setMensagens(mensagens);
            }
        });
    }

    // Trata o clique no item do menu lateral
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_cadastrar) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentoCadastrar())
                    .addToBackStack(null) // Permite voltar com o botão "back"
                    .commit();
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Fecha o menu lateral se estiver aberto ao pressionar o botão "back"
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}