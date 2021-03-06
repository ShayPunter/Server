package org.cloudburstmc.server.level.generator.standard.misc.selector;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.daporkchop.lib.common.ref.Ref;
import net.daporkchop.lib.common.ref.ThreadRef;
import net.daporkchop.lib.common.util.PValidation;
import net.daporkchop.lib.random.PRandom;
import org.cloudburstmc.server.block.BlockState;
import org.cloudburstmc.server.level.generator.standard.StandardGeneratorUtils;
import org.cloudburstmc.server.level.generator.standard.misc.ConstantBlock;
import org.cloudburstmc.server.registry.BlockRegistry;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Implementation of {@link BlockSelector} which selects a block from a pool of options.
 *
 * @author DaPorkchop_
 */
public final class MultiBlockSelector implements BlockSelector {
    protected final BlockState[] states;
    protected final BlockState[] statesWeighted;
    protected final Entry[] entries;

    public MultiBlockSelector(@NonNull Entry[] entries) {
        this.entries = Arrays.stream(entries).map(SelectionEntry::new).toArray(Entry[]::new);
        int totalWeight = Arrays.stream(this.entries).mapToInt(Entry::weight).sum();
        Preconditions.checkArgument(totalWeight > 0, "total weight (%d) must be positive!", totalWeight);
        this.states = Arrays.stream(this.entries).map(Entry::state).toArray(BlockState[]::new);
        this.statesWeighted = Arrays.stream(this.entries)
                .flatMap(entry -> entry.weight() == 1 ? Stream.of(entry) : IntStream.range(0, entry.weight()).mapToObj(i -> entry))
                .map(Entry::state).toArray(BlockState[]::new);
    }

    @Override
    public int size() {
        return this.states.length;
    }

    @Override
    public BlockState get(int index) {
        return this.states[index];
    }

    @Override
    public BlockState select(@NonNull PRandom random) {
        return this.states[random.nextInt(this.states.length)];
    }

    @Override
    public Stream<BlockState> states() {
        return Stream.of(this.states);
    }

    @Override
    public int sizeWeighted() {
        return this.statesWeighted.length;
    }

    @Override
    public BlockState getWeighted(int index) {
        return this.statesWeighted[index];
    }

    @Override
    public BlockState selectWeighted(@NonNull PRandom random) {
        return this.statesWeighted[random.nextInt(this.statesWeighted.length)];
    }

    @Override
    public Stream<Entry> entries() {
        return Arrays.stream(this.entries);
    }

    @Getter
    @Accessors(fluent = true)
    public static final class SelectionEntry implements Entry {
        private final BlockState state;
        private final int weight;

        public SelectionEntry(@NonNull BlockState state, int weight)    {
            this.state = state;
            this.weight = PValidation.ensurePositive(weight);
        }

        public SelectionEntry(@NonNull Entry entry) {
            this(entry.state(), entry.weight());
        }
    }
}
