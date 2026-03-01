# MilkyTools

一个基于 Litematica 和 Tweakeroo 材料助手系统的轻量级 Minecraft Fabric Mod。

## 功能

- **材料助手 HUD**: 当你注视一个方块时，HUD 会显示建造该方块所需的材料
- **库存计数**: 显示你背包中已有多少材料以及还缺少多少
- **智能缓存**: 缓存方块到材料的转换结果，提高性能
- **可配置**: 通过配置文件自定义 HUD 显示

## 安装

1. 将构建好的 JAR 文件放入 Minecraft 的 `mods` 文件夹
2. 下载并安装 Fabric Loader (0.18.4 或更高版本)
3. 启动游戏

## 配置文件

配置文件位于 `config/milkytools.json`:

```json
{
  "enabled": true,
  "renderHud": true,
  "hideAvailable": false,
  "hudScale": 1.0,
  "maxLines": 15,
  "updateIntervalMs": 2000
}
```

### 配置选项

- `enabled`: 启用/禁用 Mod
- `renderHud`: 启用/禁用 HUD 渲染
- `hideAvailable`: 隐藏已有足够材料的物品
- `hudScale`: HUD 缩放比例
- `maxLines`: HUD 最大显示行数
- `updateIntervalMs`: 更新间隔（毫秒）

## 构建

```bash
./gradlew build
```

构建产物位于 `build/libs/`

## 源代码参考

本项目基于以下项目的源代码改编:
- [Litematica](https://github.com/maruohon/litematica) - 材料列表系统
- [Tweakeroo](https://github.com/maruohon/tweakeroo) - 库存叠加层功能

## 许可证

MIT License

## 版本要求

- Minecraft: 1.21.10
- Fabric Loader: 0.18.4+
- Fabric API: 0.138.4+1.21.10+
- Java: 21+
