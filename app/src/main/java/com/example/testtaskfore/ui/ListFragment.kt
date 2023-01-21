package com.example.testtaskfore.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testtaskfore.R
import com.example.testtaskfore.databinding.ListFragmentBinding
import com.example.testtaskfore.ui.adapters.PhotoListAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val sharedViewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: ListFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        // enable bottom navigation view
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.VISIBLE

        recyclerView = binding.recyclerView
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        val adapter = PhotoListAdapter { currentPhoto ->
            sharedViewModel.updateCurrentPhoto(currentPhoto)
            findNavController().navigate(R.id.action_listFragment_to_detailsFragment)
        }
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            sharedViewModel.photos
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect {
                    adapter.submitList(it)
                }
        }


        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.options_menu, menu)

                val searchItem = menu.findItem(R.id.search_menu_layout)
                searchView = searchItem.actionView as SearchView

                // Keeps not finished query if fragment is recreated
                val pendingQuery = sharedViewModel.searchQuery.value
                if (pendingQuery != null && pendingQuery.isNotEmpty())
                searchItem.expandActionView()
                searchView.setQuery(pendingQuery, false)



                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                // on below line we are checking
                                // if query exist or not.
                                if (programmingLanguagesList.contains(query)) {
                                    // if query exist within list we
                                    // are filtering our list adapter.
                                    listAdapter.filter.filter(query)
                                } else {
                                    // if query is not present we are displaying
                                    // a toast message as no data found..
                                    Toast.makeText(context, "No photos found..", Toast.LENGTH_LONG)
                                        .show()
                                }
                                return false
                            }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        TODO("Not yet implemented")
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.search_menu_layout -> {
                        // put here what exactly should executed

                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    // Avoid sending empty search string when fragment destroys
    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}