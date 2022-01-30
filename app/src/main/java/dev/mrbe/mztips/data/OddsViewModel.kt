package dev.mrbe.mztips.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.mrbe.mztips.models.FilterValues
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class OddsViewModel(val oddsRepo: OddsRepo) : ViewModel() {

    //filter pref values
    private val _filterValList = mutableStateListOf(
        FilterValues(name = "All", isSelected = true),
        FilterValues(name = "Premier League", isSelected = false),
        FilterValues(name = "La Liga", false),
        FilterValues("Bundesliga", false),
        FilterValues("Seria A", false),
        FilterValues("Lige 1", false)
    )

    //create filter list
    val filterList: List<FilterValues> = _filterValList

    //set filter checkbox
    fun setFilterSelectedAtIndex(index: Int, isSelected: Boolean) {
        _filterValList[index] = _filterValList[index].copy(isSelected = isSelected)
    }


    val oddsStateFlow = MutableStateFlow<TipsResponse?>(null)

    init {
        viewModelScope.launch {
            oddsRepo.getTips().collect {
                oddsStateFlow.value = it
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class OddsViewModelFactory(private val oddsRepo: OddsRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OddsViewModel::class.java)) {
            return OddsViewModel(oddsRepo) as T
        }
        throw IllegalStateException()
    }
}