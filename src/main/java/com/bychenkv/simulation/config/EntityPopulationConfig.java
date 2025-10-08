package com.bychenkv.simulation.config;

public class EntityPopulationConfig {
    private static final int DEFAULT_ROCKS_NUMBER = 5;
    private static final int DEFAULT_TREES_NUMBER = 5;
    private static final int DEFAULT_GRASS_NUMBER = 5;
    private static final int DEFAULT_HERBIVORES_NUMBER = 5;
    private static final int DEFAULT_PREDATORS_NUMBER = 5;

    private final int rocksNumber;
    private final int treesNumber;
    private final int grassNumber;
    private final int herbivoresNumber;
    private final int predatorsNumber;

    private EntityPopulationConfig(Builder builder) {
        this.rocksNumber = builder.rocksNumber;
        this.treesNumber = builder.treesNumber;
        this.grassNumber = builder.grassNumber;
        this.herbivoresNumber = builder.herbivoresNumber;
        this.predatorsNumber = builder.predatorsNumber;
    }

    public static EntityPopulationConfig defaultConfig() {
        return new Builder().build();
    }

    public int getRocksNumber() {
        return rocksNumber;
    }

    public int getTreesNumber() {
        return treesNumber;
    }

    public int getGrassNumber() {
        return grassNumber;
    }

    public int getHerbivoresNumber() {
        return herbivoresNumber;
    }

    public int getPredatorsNumber() {
        return predatorsNumber;
    }

    public static class Builder {
        private int rocksNumber = DEFAULT_ROCKS_NUMBER;
        private int treesNumber = DEFAULT_TREES_NUMBER;
        private int grassNumber = DEFAULT_GRASS_NUMBER;
        private int herbivoresNumber = DEFAULT_HERBIVORES_NUMBER;
        private int predatorsNumber = DEFAULT_PREDATORS_NUMBER;

        public Builder rocks(int rocksNumber) {
            this.rocksNumber = rocksNumber;
            return this;
        }

        public Builder grass(int grassNumber) {
            this.grassNumber = grassNumber;
            return this;
        }

        public Builder trees(int treesNumber) {
            this.treesNumber = treesNumber;
            return this;
        }

        public Builder herbivores(int herbivoresNumber) {
            this.herbivoresNumber = herbivoresNumber;
            return this;
        }

        public Builder predators(int predatorsNumber) {
            this.predatorsNumber = predatorsNumber;
            return this;
        }

        public EntityPopulationConfig build() {
            return new EntityPopulationConfig(this);
        }
    }
}
