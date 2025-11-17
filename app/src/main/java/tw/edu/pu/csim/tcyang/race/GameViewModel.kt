package tw.edu.pu.csim.qinghong179.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

// 根據您的需求，將 Horse 屬性名稱保持為 HorseX 和 HorseY
data class Horse(val number: Int) {
    // 必須使用 mutableStateOf 或 mutableIntStateOf 才能在 Compose 中觸發重繪
    var HorseX by mutableIntStateOf(0)
    var HorseY by mutableIntStateOf(100 + 320 * number) // 根據您的舊邏輯設置初始 Y 座標
    var HorseNo by mutableIntStateOf(0) // 圖片編號 (0~3)
    private var speed by mutableIntStateOf(Random.nextInt(10, 30)) // 新增速度屬性，用於隨機移動

    fun Run(){
        // 賽馬圖片處理 (切換動畫幀)
        HorseNo ++
        if (HorseNo > 3){
            HorseNo = 0
        }

        // 馬匹 X 座標移動
        HorseX += speed
        // 模擬速度隨機變化
        speed = Random.nextInt(10, 30)
    }
}