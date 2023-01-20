package com.bumble.appyx.transitionmodel.cards.operation

import com.bumble.appyx.transitionmodel.cards.CardsModel
import com.bumble.appyx.interactions.Parcelize

@Parcelize
class VotePass<NavTarget> : TopCardOperation<NavTarget>(CardsModel.State.VotePass)