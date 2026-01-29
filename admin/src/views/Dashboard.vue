<template>
  <div class="dashboard-container">
    <el-card class="dashboard-card">
      <template #header>
        <div class="card-header">
          <span>数据概览</span>
        </div>
      </template>
      
      <!-- 数据统计卡片 -->
      <div class="stats-container">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">128</div>
            <div class="stat-label">今日新增用户</div>
          </div>
          <div class="stat-icon">
            <el-icon><UserFilled /></el-icon>
          </div>
        </el-card>
        
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">64</div>
            <div class="stat-label">今日订单量</div>
          </div>
          <div class="stat-icon">
            <el-icon><ShoppingBag /></el-icon>
          </div>
        </el-card>
        
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">¥2,560</div>
            <div class="stat-label">总交易额</div>
          </div>
          <div class="stat-icon">
            <el-icon><Money /></el-icon>
          </div>
        </el-card>
        
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">23</div>
            <div class="stat-label">待审核书籍</div>
          </div>
          <div class="stat-icon">
            <el-icon><Document /></el-icon>
          </div>
        </el-card>
      </div>
      
      <!-- 数据趋势图表 -->
      <div class="charts-container">
        <el-card class="chart-card">
          <template #header>
            <span>用户增长趋势</span>
          </template>
          <div class="chart-content">
            <!-- 这里可以集成ECharts等图表库 -->
            <div style="height: 300px; display: flex; align-items: center; justify-content: center; color: #909399;">
              用户增长趋势图表
            </div>
          </div>
        </el-card>
        
        <el-card class="chart-card">
          <template #header>
            <span>订单交易趋势</span>
          </template>
          <div class="chart-content">
            <!-- 这里可以集成ECharts等图表库 -->
            <div style="height: 300px; display: flex; align-items: center; justify-content: center; color: #909399;">
              订单交易趋势图表
            </div>
          </div>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { UserFilled, ShoppingBag, Money, Document } from '@element-plus/icons-vue'
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.dashboard-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stats-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #606266;
}

.stat-icon {
  font-size: 32px;
  color: #409EFF;
}

.charts-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.chart-card {
  padding: 20px;
}

.chart-content {
  margin-top: 20px;
}

@media (max-width: 1200px) {
  .stats-container {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .charts-container {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .stats-container {
    grid-template-columns: 1fr;
  }
}
</style>
