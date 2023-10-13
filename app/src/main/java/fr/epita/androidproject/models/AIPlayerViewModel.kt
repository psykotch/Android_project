package fr.epita.androidproject.models

class AIPlayerViewModel : PlayerViewModel() {
    private val diceViewModel = DiceViewModel()

    init {
        this.name.postValue("ai player")
    }

    fun makeMove(): Array<String> {

        val rolledDice = diceViewModel.roll()
        var one = 0
        var two = 0
        var three = 0
        val rolledDiceString: ArrayList<String> = ArrayList()
        // Implement AI logic based on the rolled dice
        for (dice in rolledDice) {
            when (dice) {
                DiceViewModel.diceFace.ONE -> {
                    rolledDiceString.add("ONE")
                    one += 1
                }

                DiceViewModel.diceFace.TWO -> {
                    rolledDiceString.add("TWO")
                    two += 1
                }

                DiceViewModel.diceFace.THREE -> {
                    rolledDiceString.add("THREE")

                    three += 1
                }

                DiceViewModel.diceFace.ENERGY -> {
                    rolledDiceString.add("ENERGY")

                    this.energy.postValue(this.energy.value?.plus(1))
                }

                DiceViewModel.diceFace.ATTACK -> {
                    rolledDiceString.add("ATTACK")

                    //   attack += 1
                }

                DiceViewModel.diceFace.LIFE -> {
                    rolledDiceString.add("LIFE")

                    this.life.postValue(this.life.value?.plus(1))
                }
            }
        }
        this.score.postValue(this.score.value?.plus(one / 3))
        this.score.postValue(this.score.value?.plus(two / 3))
        this.score.postValue(this.score.value?.plus(three / 3))

        return rolledDiceString.toTypedArray()
    }
}