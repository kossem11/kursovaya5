package production.kossem.csa_build

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SearchViewModel(private val state: SavedStateHandle) : ViewModel() {

    companion object {
        private const val QUERY_KEY = "search_query"
    }

    var searchQuery: String?
        get() = state.get(QUERY_KEY)
        set(value) {
            state.set(QUERY_KEY, value)
        }
}