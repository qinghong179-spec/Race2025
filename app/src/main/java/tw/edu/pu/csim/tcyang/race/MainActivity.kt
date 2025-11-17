package com.example.race //

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.window.layout.WindowMetricsCalculator
import tw.edu.pu.csim.tcyang.race.ui.theme.RaceTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //強迫橫式螢幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        // 隱藏狀態列與導航列
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

        // 確保內容延伸到至邊緣
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 步驟 1-3: 獲取螢幕像素尺寸
        val windowMetricsCalculator = WindowMetricsCalculator.getOrCreate()
        val currentWindowMetrics = windowMetricsCalculator.computeCurrentWindowMetrics(this)
        val bounds = currentWindowMetrics.bounds
        val screenWidthPx = bounds.width().toFloat()
        val screenHeightPx = bounds.height().toFloat()

        // 實例化 ViewModel
        // 假設 GameViewModel 位於 com.example.race 包內
        val gameViewModel: GameViewModel by viewModels()
        gameViewModel.SetGameSize(screenWidthPx,screenHeightPx)

        setContent {
            RaceTheme {
                // (1) 修正：更新 message 內容
                // GameScreen 位於同一包內，可以直接使用
                GameScreen(message="賽馬遊戲(作者：洪詩晴) 。", gameViewModel)
            }
        }
    }
}