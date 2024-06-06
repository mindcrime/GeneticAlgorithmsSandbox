package org.fogbeam.example.ai_exp.geneticalgorithms;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.Factory;

public class JeneticsMain1 
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
				Genotype.of( BitChromosome.of(10, 0.5 ));
		
		
		// create the execution environment
		final Engine<BitGene, Integer> engine = Engine
				.builder(JeneticsMain1::eval, gtf )
				.build();
		
		
		
		final EvolutionStatistics<Integer, ?> statistics
				= EvolutionStatistics.ofNumber();
		
		
		// start the execution (evolution) and
		// collect the results
		final Genotype<BitGene> result = engine.stream()
				.limit(100)
				.peek( statistics )
				.collect(EvolutionResult.toBestGenotype());
		
		
		System.out.println( "Statistics:\n" + statistics );
		System.out.println( "Results:\n " + result );
		
		
		System.out.println( "done" );
	}

}
