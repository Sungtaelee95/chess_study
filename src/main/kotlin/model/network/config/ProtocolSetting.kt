package model.network.config

enum class ProtocolSetting(val value: Int) {
    HEAD_LENGTH(1),
    DATA_LENGTH(4),
    POSITION_DATA_LENGTH(4),
    COLOR_DATA_LENGTH(1)
}