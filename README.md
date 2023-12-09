# FPS 地图

## 介绍

FPS 地图是一个 Minecraft 地图，可以在游戏中显示玩家位置。

## 自定义地图配置

### 配置文件

* fpsmap-client.json

```json5
{
  // 默认敌人颜色
  "default_enemy_color": "red",
  // 默认队友颜色
  "default_teammate_color": "green",
  // 是否按照队伍颜色显示
  "team_color": true,
  // 地图尺寸
  "map_size": 1.0
}
```

* fps-server.json

```json5
{
  // 是否显示未完全暴露玩家
  "show_hidden_player": true,
  // 是否共享队友位置
  "share_teammate_position": true,
  // 是否共享队友视野
  "share_teammate_view": true
}
```

### 存档配置文件

* 目录结构

```
.minecraft/
├── ...
└── versions/
    └── <version>/
        ├── ...
        ├── config/
        │   ├── ...
        │   ├── fpsmap-client.json
        │   └── fpsmap-server.json
        ├── saves/
        │   ├── ...
        │   └── <map name>/
        │       ├── advancements/
        │       ├── data/
        │       ├── datapacks/
        │       ├── DIM1/
        │       ├── DIM-1/
        │       ├── ...
        │       └── fpsmap.json
        └── resourcepacks/
            ├── ...
            └── <resource pack name>.zip/
                ├── assets/
                │   ├── minecraft/
                │   └── fpsmap/
                │       ├── ...
                │       └── textures/
                │           ├── map1.png
                │           ├── map2.png
                │           └── map3.png
                ├── pack.mcmeta
                └── pack.png
```

* fpsmap.json

```json5
[
  {
    // 维度名称
    "dim": "default",
    // 地图图片
    "image": "fpsmap:map1",
    // 地图中心（x,z）
    "center": [
      0,
      0
    ],
    // 地图范围
    "range": 256
  },
  {
    "dim": "nether",
    "image": "fpsmap:map2",
    "center": [
      25,
      7
    ]
  },
  {
    "dim": "the_end",
    "image": "fpsmap:map3"
  }
]
```
