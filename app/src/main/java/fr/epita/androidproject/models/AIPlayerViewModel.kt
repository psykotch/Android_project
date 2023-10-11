package fr.epita.androidproject.models

class AIPlayerViewModel : PlayerViewModel() {
    private val diceViewModel = DiceViewModel()

    fun makeMove(): Array<DiceViewModel.diceFace> {

        val rolledDice = diceViewModel.roll()
        var one = 0
        var two = 0
        var three = 0
        // Implement AI logic based on the rolled dice
        for (dice in rolledDice) {
            when (dice) {
                DiceViewModel.diceFace.ONE -> {
                    one += 1
                }

                DiceViewModel.diceFace.TWO -> {
                    two += 1
                }

                DiceViewModel.diceFace.THREE -> {
                    three += 1
                }

                DiceViewModel.diceFace.ENERGY -> {
                    this.energy.postValue(this.energy.value?.plus(1))
                }

                DiceViewModel.diceFace.ATTACK -> {
                 //   attack += 1
                }

                DiceViewModel.diceFace.LIFE -> {
                    this.life.postValue(this.life.value?.plus(1))
                }
            }
        }
        this.score.postValue(this.score.value?.plus(one/3))
        this.score.postValue(this.score.value?.plus(two/3))
        this.score.postValue(this.score.value?.plus(three/3))

        return rolledDice
    }
}