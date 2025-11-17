package tw.edu.pu.csim.qinghong179.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch




class GameViewModel: ViewModel() {
    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set
    var gameRunning by mutableStateOf(false)
    var circleX by mutableStateOf(0f)
    var circleY by mutableStateOf(0f)

    // 新增：儲存獲勝訊息的狀態
    var winningMessage by mutableStateOf("")

    val horses = mutableListOf<Horse>()

    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        for(i in 0..2){
            horses.add(Horse(i))
        }
    }

    // 新增：重設所有馬匹位置到起點
    private fun resetHorses() {
        for(i in 0..2){
            horses[i].HorseX = 0
        }
    }

    fun StartGame() {
        //回到初使位置
        circleX = 100f
        circleY = screenHeightPx - 100f

        // 重置獲勝訊息並將馬匹歸零，準備下一輪
        winningMessage = ""
        resetHorses()

        viewModelScope.launch {
            while (gameRunning) { // 每0.1秒循環
                delay(100)

                // 圓形移動邏輯
                circleX += 10
                if (circleX >= screenWidthPx - 100){
                    circleX = 100f
                }

                var winnerIndex: Int? = null
                val finishLine = screenWidthPx - 200f // 馬匹圖片寬度為 200

                for(i in 0..2){
                    horses[i].Run()

                    // (2) 檢查是否獲勝
                    if(horses[i].HorseX >= finishLine){
                        // 記錄獲勝者
                        winnerIndex = i
                        break // 找到獲勝者後立即跳出循環
                    }
                }

                // 在循環結束後處理獲勝邏輯
                if (winnerIndex != null) {
                    gameRunning = false // 停止遊戲
                    // 馬匹編號 (number) 從 0 開始，顯示時要 + 1
                    winningMessage = "第${winnerIndex + 1}馬獲勝"
                    // 馬匹位置已在 StartGame() 開頭重設，等待下一輪
                }
            }
        }
    }
    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }
}