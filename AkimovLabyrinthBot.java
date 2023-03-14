package override.example;

import override.logic.Direction;
import override.logic.GameState;
import override.logic.LabyrinthPlayer;

public class AkimovLabyrinthBot implements LabyrinthPlayer {

    private int myNumber;
    boolean needLeft;
    boolean needRight;
    boolean needUp;
    boolean needDown;
    boolean reverse;

    @Override
    public void takeYourNumber(int number) {
        myNumber = number;
    }

    @Override
    public Direction step(GameState gameState) {
        int[][] map = gameState.getMap();
        int currentHeight = 0;
        int currentWidth = 0;


        for (int i = 0; i < gameState.HEIGHT; i++) {
            for (int j = 0; j < gameState.WIDTH; j++) {
                if (map[i][j] == myNumber) {
                    currentHeight = i;
                    currentWidth = j;
                }
            }
        }

        if ((currentHeight == 0 || currentHeight == gameState.HEIGHT - 1) && currentWidth == 0) {
            reverse = true;
        }
        if (currentWidth == 14 && (currentHeight == 14 || currentHeight == 0)) {
            reverse = false;
        }

        if (reverse) {
            if (currentHeight == 0 && currentWidth == 0) {
                needRight = true;
                needLeft = false;
                //   return Direction.RIGHT;
            }
            if (currentHeight == gameState.HEIGHT - 1 && currentWidth == 0) {
                needRight = true;
            }

        }

        if (needRight) {
            if (reverse) {

                if ((currentHeight + 1) < gameState.HEIGHT) {
                    if (map[currentHeight + 1][currentWidth] == -1) {
                        return Direction.NONE;
                    }
                    if (needUp) {
                        if (map[currentHeight][currentWidth + 1] >= 0 && !needLeft) {
                            needUp = false;
                            return Direction.RIGHT;
                        }
                    }
                    if (map[currentHeight + 1][currentWidth] >= 0 && !needUp) {
                        return Direction.BOTTOM;
                    } else if ((currentWidth + 1) < gameState.WIDTH) {
                        if (map[currentHeight][currentWidth + 1] >= 0 && !needLeft) {
                            return Direction.RIGHT;
                        } else if (needUp == false) {
                            if (currentHeight != 0) {
                                if (map[currentHeight - 1][currentWidth] >= 0) {
                                    needUp = true;
                                    return Direction.UP;
                                } else if (map[currentHeight][currentWidth - 1] >= 0) {
                                    needRight = false;
                                    needLeft = true;
                                } else {
                                    return Direction.BOTTOM;
                                }
                            } else {
                                needRight = true;
                                return Direction.LEFT;
                            }
                        }
                    }
                } else if (currentHeight == gameState.HEIGHT - 1) {
                    if (needRight) {
                        if ((currentWidth + 1) >= 0 && !needLeft) {
                            if (map[currentHeight][currentWidth + 1] >= 0) {
                                needRight = false;
                                needUp = true;
                                needDown = false;
                                return Direction.RIGHT;
                            } else {
                                needUp = true;
                                return Direction.UP;
                            }

                        } else {
                            needUp = true;
                            return Direction.UP;
                        }
                    }
                }
            } else {
                if ((currentWidth + 1) < gameState.WIDTH && (currentHeight - 1 < 0)) {
                    if (map[currentHeight][currentWidth + 1] >= 0) {
                        needRight = true;
                        return Direction.RIGHT;
                    } else {
                        needRight = false;
                    }
                } else {
                    needRight = false;
                    if (currentHeight != 0) {
                        return Direction.UP;
                    } else {
                        return Direction.BOTTOM;
                    }

                }
            }

        }


        if (needUp) {
            if (reverse) {
                if ((currentHeight - 1) >= 0) {
                    if (map[currentHeight - 1][currentWidth] >= 0 && !needDown) {
                        needUp = true;
                        return Direction.UP;
                    } else if ((currentWidth != gameState.WIDTH - 1) && map[currentHeight][currentWidth + 1] >= 0 && !needLeft) {
                        return Direction.RIGHT;
                    } else if (map[currentHeight][currentWidth - 1] >= 0) {
                        needRight = false;
                        needLeft = true;
                    } else {
                        needDown = true;
                        needRight = true;
                        return Direction.BOTTOM;
                    }
                } else if (currentHeight == 0) {
                    needUp = false;
                    if ((currentWidth + 1) < gameState.WIDTH) {
                        if (map[currentHeight][currentWidth + 1] >= 0) {
                            needRight = true;
                            return Direction.RIGHT;
                        } else {
                            if (map[currentHeight + 1][currentWidth] >= 0) {
                                needDown = true;
                                return Direction.BOTTOM;
                            } else if (map[currentHeight][currentWidth - 1] >= 0) {
                                needLeft = true;
                            }

                        }
                    }
                } else {
                    needUp = false;
                    if ((currentWidth + 1) < gameState.WIDTH) {
                        if (map[currentHeight][currentWidth + 1] >= 0) {
                            needRight = true;
                            return Direction.RIGHT;
                        } else {
                            if (map[currentHeight][currentWidth - 1] >= 0) {
                                needLeft = true;
                            } else {
                                needRight = true;
                                return Direction.BOTTOM;
                            }
                        }
                    }
                }
            } else {
                if ((currentWidth - 1) >= 0) {
                    if (map[currentHeight][currentWidth - 1] >= 0) {
                        needUp = false;
                        return Direction.LEFT;
                    }
                    if ((currentHeight - 1) >= 0) {
                        if (map[currentHeight - 1][currentWidth] >= 0) {
                            needUp = true;
                            return Direction.UP;
                        } else if (currentWidth + 1 != gameState.WIDTH) {
                            if (map[currentHeight][currentWidth + 1] >= 0) {
                                return Direction.RIGHT;
                            } else return Direction.BOTTOM;
                        }
                    } else if (map[currentHeight][currentWidth - 1] >= 0) {
                        needUp = false;
                    } else if (currentWidth + 1 != gameState.WIDTH) {
                        if (map[currentHeight][currentWidth + 1] >= 0) {
                            return Direction.RIGHT;
                        } else return Direction.BOTTOM;
                    }
                } else if ((currentHeight - 1) >= 0) {
                    if (map[currentHeight - 1][currentWidth] >= 0) {
                        needUp = true;
                        return Direction.UP;
                    }
                } else {
                    needRight = true;
                    return Direction.RIGHT;
                }
            }

        }

        if (needDown) {
            if (reverse) {
                if ((currentHeight + 1) < gameState.HEIGHT && map[currentHeight][currentWidth - 1] < 0) {
                    if (map[currentHeight + 1][currentWidth] >= 0) {
                        needDown = true;
                        return Direction.BOTTOM;
                    } else if (map[currentHeight][currentWidth + 1] >= 0 && (currentWidth != gameState.WIDTH)) {
                        needDown = false;
                        return Direction.RIGHT;
                    }
                } else {
                    needDown = false;
                    needLeft = true;
                    return Direction.LEFT;
                }
            } else {
                if ((currentWidth - 1) >= 0) {
                    if (map[currentHeight][currentWidth - 1] >= 0) {
                        needDown = false;
                        return Direction.LEFT;
                    } else if (map[currentHeight - 1][currentWidth] >= 0) {
                        needDown = true;
                        return Direction.BOTTOM;
                    } else if (currentWidth + 1 != gameState.WIDTH) {
                        return Direction.RIGHT;
                    } else {
                        return Direction.BOTTOM;
                    }

                } else if (map[currentHeight - 1][currentWidth] >= 0) {
                    needDown = true;
                    return Direction.BOTTOM;
                }
            }
        }

        if (needLeft) {
            if (reverse) {
                if (currentHeight != 0) {
                    if (map[currentHeight - 1][currentWidth] < 0) {
                        if (map[currentHeight][currentWidth - 1] >= 0) {
                            needUp = false;
                            needDown = false;
                            needLeft = true;
                            return Direction.LEFT;
                        } else if (map[currentHeight - 1][currentWidth] < 0) {
                            needDown = true;
                            needLeft = false;
                        } else {
                            needLeft = false;
                            needUp = true;
                        }
                    } else {
                        needUp = true;
                        needDown = false;
                        needLeft = false;
                    }

                } else {
                    needUp = false;
                    needDown = true;
                    needLeft = false;
                }

            } else {
                if ((currentHeight + 1) < gameState.HEIGHT) {
                    if (map[currentHeight + 1][currentWidth] >= 0) {
                        return Direction.BOTTOM;
                    } else if ((currentWidth - 1) >= 0) {
                        if (map[currentHeight][currentWidth - 1] >= 0) {
                            return Direction.LEFT;
                        } else if (needUp == false) {
                            if (currentHeight != 0) {
                                if (map[currentHeight - 1][currentWidth] >= 0) {
                                    needUp = true;
                                    return Direction.UP;
                                } else {
                                    needRight = true;
                                    return Direction.RIGHT;
                                }
                            } else {
                                needRight = true;
                                return Direction.RIGHT;
                            }
                        }
                    }
                } else if (currentHeight == gameState.HEIGHT - 1) {
                    if (needLeft) {
                        if ((currentWidth - 1) >= 0) {
                            if (map[currentHeight][currentWidth - 1] >= 0) {
                                needLeft = false;
                                return Direction.LEFT;
                            } else {
                                needUp = true;
                                return Direction.UP;
                            }

                        }
                    }
                }
            }
        } else {

            if ((currentHeight - 1) >= 0) {
                if (map[currentHeight - 1][currentWidth] >= 0) {
                    return Direction.UP;
                } else if ((currentWidth - 1) >= 0) {
                    if (map[currentHeight][currentWidth - 1] >= 0) {
                        return Direction.LEFT;
                    } else if (needDown == false) {
                        needDown = true;
                        return Direction.BOTTOM;
                    }
                } else if (map[currentHeight][currentWidth + 1] >= 0) {
                    needRight = true;
                    return Direction.RIGHT;
                } else if (needDown == false) {
                    needDown = true;
                    return Direction.BOTTOM;
                }

            } else if (currentHeight == 0) {
                if (needLeft == false) {
                    if ((currentWidth - 1) >= 0) {
                        if (map[currentHeight][currentWidth - 1] >= 0) {
                            needLeft = true;
                            return Direction.LEFT;
                        } else if (needDown == false) {
                            needDown = true;
                            return Direction.BOTTOM;
                        }
                    }
                }

            }
        }
        return Direction.NONE;
    }

    @Override
    public String getTelegramNick() {
        return "@NikitaAkimovv";
    }
}

