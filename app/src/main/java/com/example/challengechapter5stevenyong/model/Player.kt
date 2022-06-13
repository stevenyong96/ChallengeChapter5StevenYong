package com.example.challengechapter5stevenyong.model

import java.io.Serializable

class Player(): Serializable {
    var playerPick: Int = 0
        get() = field        // getter
        set(value) {         // setter
            field = value
        }
    var playerName: String = ""
        get() = field        // getter
        set(value) {         // setter
            field = value
        }
    var playerPickDesc: String = ""
        get() = field        // getter
        set(value) {         // setter
            field = value
        }

}