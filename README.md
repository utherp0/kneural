# kneural

Neural Net simulation using Knative Neurons, Cloud Events and Data Grid Memory

# Precis
This project aims to simulate the behaviour of neurons in an aggregated net using individual Knative functions as processors, backed by memory in Infinispan and driven entirely by Cloud Events.

Each Neuron will have one or more triggers; these triggers will link the neuron to the Knative broker which will act as the conduit for messages into the Neurons. 

The neurons will work by using the payload from the Cloud Event, which will identify the type of event (assuming Knative functions cannot deduce the Event type from the Cloud Event; normal processing of the Cloud Event involves the type being stripped off and processed for triggers, with just the payload being delivered; the constructors of these events will add the type into the payload itself for use within the Neurons), grab the memory state for the Neuron from the Data Grid (each Neuron will have a unique state stored externally to the Neuron to allow Knative to scale the Neurons down without losing the persisted state), use the Cloud Event type and payload to determine threshold behaviour, and then if the individual rules for the Neuron indicate the Cloud Event has pushed the Neuron past its threshold, the Neuron itself will issue a Cloud Event to the broker.

The neuron will store its post-processing state in the Data Grid as a last action. 

This thresholding will allow the neurons to mimic the behaviour of actual neurons; a neuron in real life acts by aggregating the inputs and providing an output when the combination of the inputs exceeds the threshold. 

These Kneural neurons will be able to do any processing based on the inputs provided, rather than a simple analogue aggregation. 

# Design
The system will be designed as a set of deployable knative services, initially in Quarkus/JAVA, along with an ephemeral (initially) broker. These functions will be deployed in the same namespace of an OpenShift cluster.

The memory/persistence will be provided by an install of Red Hat Data Grid. This will also be done on the OpenShift cluster. The Neurons will communicate with the Data Grid using the RESTful cache interface, requesting and updating information uniquely identified to each neuron. 

Additional services will be provided for a dashboard for the system based on the state stored in the Data Grid. This state will be the working data and experiment results.

# Notes
To get the u/p for access to the Infinispan by default use:

oc get secret infinispan-generated-secret -o jsonpath="{.data.identities\.yaml}" | base64 --decode
