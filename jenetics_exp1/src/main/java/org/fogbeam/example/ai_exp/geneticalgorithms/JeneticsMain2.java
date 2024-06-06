package org.fogbeam.example.ai_exp.geneticalgorithms;

import static io.jenetics.engine.Limits.bySteadyFitness;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.Factory;

public class JeneticsMain2 
{
	private static int eval( final Genotype<BitGene> gt )
	{
		return gt.getChromosome().as(BitChromosome.class).bitCount();
	}
	
	
	public static void main(String[] args) 
	{

		// define the genotype (factory) suitable for
		// the problem
		final Factory<Genotype<BitGene>> gtf =
				Genotype.of( BitChromosome.of(20, 0.5 ));
		
		
		// create the execution environment
		final Engine<BitGene, Integer> engine = Engine
				.builder(JeneticsMain2::eval, gtf )
				.populationSize(600)
				.selector( new RouletteWheelSelector<>())
				.alterers(new Mutator<>(0.05), 
						  new SinglePointCrossover<>(0.06))
				.build();
		
		
		final EvolutionStatistics<Integer, ?> statistics
				= EvolutionStatistics.ofNumber();
		
		
		// start the execution (evolution) and
		// collect the results
		final Genotype<BitGene> result = engine.stream()
				.limit(bySteadyFitness(10))
				.limit(100)
				.peek( statistics )
				.collect(EvolutionResult.toBestGenotype());
		
		
		System.out.println( "Statistics:\n" + statistics );
		System.out.println( "Results:\n " + result );
		
		
		System.out.println( "done" );
	}

}
