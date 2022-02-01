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
        FilterValues("Champions League", false),
        FilterValues("Europa League", false),
        FilterValues(name = "Premier League", isSelected = false),
        FilterValues(name = "La Liga", false),
        FilterValues("Bundesliga", false),
        FilterValues("Seria A", false),
        FilterValues("Lige 1", false)
    )

    //create filter list
    val filterList: List<FilterValues> = _filterValList


    //state flow for tips
    val oddsStateFlow = MutableStateFlow<TipsResponse?>(null)

    init {
        defaultLoading()
    }


    private fun defaultLoading() {
        viewModelScope.launch {
            oddsRepo.getTips(listOf("Champions League")).collect {
                oddsStateFlow.value = it
            }
        }
    }
//     fun otherLoading(filter: String?) {
//        viewModelScope.launch {
//            oddsRepo.getTips(filter).collect{
//                oddsStateFlow.value = it
//            }
//        }
//    }

    fun getFilterParamsAndLoadData(filterParams: List<String?>) {
        viewModelScope.launch {
            oddsRepo.getTips(filterParams).collect {
                oddsStateFlow.value = it
            }
        }
    }
}


//view model factory
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